package com.rlb.oc.outbox.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DuplicateKeyException;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.OrderUpdateEvent;
import com.rlb.oc.kafka.producer.OrderPublisher;
import com.rlb.oc.kafka.producer.OrderUpdatePublisher;
import com.rlb.oc.outbox.model.OutboxEvent;
import com.rlb.oc.outbox.model.OutboxEventStatus;
import com.rlb.oc.outbox.model.OutboxEventType;
import com.rlb.oc.outbox.repo.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class OutboxPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OutboxPublisher.class);

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private final OrderPublisher orderPublisher;
    private final OrderUpdatePublisher orderUpdatePublisher;

    private final int maxAttempts;
    private final Duration baseBackoff;

    public OutboxPublisher(
            OutboxEventRepository outboxEventRepository,
            ObjectMapper objectMapper,
            OrderPublisher orderPublisher,
            OrderUpdatePublisher orderUpdatePublisher,
            @Value("${rlb.outbox.maxAttempts:25}") int maxAttempts,
            @Value("${rlb.outbox.baseBackoffSeconds:5}") long baseBackoffSeconds
    ) {
        this.outboxEventRepository = Objects.requireNonNull(outboxEventRepository, "outboxEventRepository must not be null");
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
        this.orderPublisher = Objects.requireNonNull(orderPublisher, "orderPublisher must not be null");
        this.orderUpdatePublisher = Objects.requireNonNull(orderUpdatePublisher, "orderUpdatePublisher must not be null");
        this.maxAttempts = maxAttempts;
        this.baseBackoff = Duration.ofSeconds(baseBackoffSeconds);
    }

    @Scheduled(fixedDelayString = "${rlb.outbox.pollDelayMs:2000}")
    public void publishBatch() {
        Instant now = Instant.now();
        List<OutboxEvent> events = outboxEventRepository
                .findTop200ByStatusInAndNextAttemptAtLessThanEqualOrderByCreatedAtAsc(
                        Set.of(OutboxEventStatus.NEW, OutboxEventStatus.FAILED),
                        now
                );

        if (events.isEmpty()) {
            return;
        }

        for (OutboxEvent event : events) {
            publishOne(event);
        }
    }

    private void publishOne(OutboxEvent outboxEvent) {
        if (outboxEvent.getStatus() == OutboxEventStatus.SENT) {
            return;
        }

        int attempts = outboxEvent.getAttempts();
        if (attempts >= maxAttempts) {
            if (outboxEvent.getStatus() != OutboxEventStatus.FAILED) {
                outboxEvent.setStatus(OutboxEventStatus.FAILED);
                outboxEvent.setUpdatedAt(Instant.now());
                outboxEvent.setLastError("maxAttempts exceeded");
                outboxEventRepository.save(outboxEvent);
            }
            return;
        }

        try {
            // Mark as PROCESSING to reduce duplicate work across instances (best-effort; still needs idempotent consumers).
            outboxEvent.setStatus(OutboxEventStatus.PROCESSING);
            outboxEvent.setUpdatedAt(Instant.now());
            outboxEventRepository.save(outboxEvent);

            switch (outboxEvent.getEventType()) {
                case ORDER_CREATED -> publishOrderCreated(outboxEvent);
                case ORDER_UPDATED -> publishOrderUpdated(outboxEvent);
                default -> throw new IllegalStateException("Unsupported outbox event type: " + outboxEvent.getEventType());
            }

            outboxEvent.setStatus(OutboxEventStatus.SENT);
            outboxEvent.setUpdatedAt(Instant.now());
            outboxEvent.setLastError(null);
            outboxEventRepository.save(outboxEvent);

            logger.info(
                    "Outbox event published. outboxId='{}', type={}, aggregateId='{}', attempts={}",
                    outboxEvent.getId(),
                    outboxEvent.getEventType(),
                    outboxEvent.getAggregateId(),
                    outboxEvent.getAttempts()
            );
        } catch (Exception ex) {
            int nextAttempts = outboxEvent.getAttempts() + 1;
            outboxEvent.setAttempts(nextAttempts);
            outboxEvent.setStatus(OutboxEventStatus.FAILED);
            outboxEvent.setUpdatedAt(Instant.now());
            outboxEvent.setLastError(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            outboxEvent.setNextAttemptAt(Instant.now().plus(backoff(nextAttempts)));

            try {
                outboxEventRepository.save(outboxEvent);
            } catch (DuplicateKeyException ignore) {
                // best-effort; if this happens, another instance may have updated it.
            }

            logger.error(
                    "Outbox publish failed. outboxId='{}', type={}, aggregateId='{}', attempts={}",
                    outboxEvent.getId(),
                    outboxEvent.getEventType(),
                    outboxEvent.getAggregateId(),
                    outboxEvent.getAttempts(),
                    ex
            );
        }
    }

    private void publishOrderCreated(OutboxEvent outboxEvent) throws Exception {
        if (outboxEvent.getEventType() != OutboxEventType.ORDER_CREATED) {
            throw new IllegalArgumentException("Expected ORDER_CREATED event");
        }
        OrderCreateEvent event = objectMapper.readValue(outboxEvent.getPayloadJson(), OrderCreateEvent.class);
        orderPublisher.publishOrderPlaceEvent(event);
    }

    private void publishOrderUpdated(OutboxEvent outboxEvent) throws Exception {
        if (outboxEvent.getEventType() != OutboxEventType.ORDER_UPDATED) {
            throw new IllegalArgumentException("Expected ORDER_UPDATED event");
        }
        OrderUpdateEvent event = objectMapper.readValue(outboxEvent.getPayloadJson(), OrderUpdateEvent.class);
        orderUpdatePublisher.publishOrderUpdateEvent(event);
    }

    private Duration backoff(int attempts) {
        // Exponential-ish backoff with a soft cap.
        long factor = Math.min(1L << Math.min(attempts, 10), 1024L);
        Duration d = baseBackoff.multipliedBy(factor);
        Duration cap = Duration.ofMinutes(10);
        return d.compareTo(cap) > 0 ? cap : d;
    }
}

