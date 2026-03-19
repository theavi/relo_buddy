package com.rlb.oc.outbox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.OrderUpdateEvent;
import com.rlb.oc.outbox.model.OutboxEvent;
import com.rlb.oc.outbox.model.OutboxEventType;
import com.rlb.oc.outbox.repo.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class OutboxService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OutboxService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.outboxEventRepository = Objects.requireNonNull(outboxEventRepository, "outboxEventRepository must not be null");
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
    }

    public OutboxEvent enqueueOrderCreated(OrderCreateEvent event) {
        validateOrderCreated(event);
        return save(new OutboxEvent(
                OutboxEventType.ORDER_CREATED,
                "Order",
                event.getId(),
                toJson(event)
        ));
    }

    public OutboxEvent enqueueOrderUpdated(OrderUpdateEvent event) {
        validateOrderUpdated(event);
        return save(new OutboxEvent(
                OutboxEventType.ORDER_UPDATED,
                "Order",
                event.getId(),
                toJson(event)
        ));
    }

    @SuppressWarnings("NullAway")
    private OutboxEvent save(OutboxEvent outboxEvent) {
        return outboxEventRepository.save(outboxEvent);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize outbox payload", e);
        }
    }

    private void validateOrderCreated(OrderCreateEvent event) {
        if (event == null || !StringUtils.hasText(event.getId())) {
            throw new IllegalArgumentException("OrderCreateEvent.id must not be null/blank");
        }
    }

    private void validateOrderUpdated(OrderUpdateEvent event) {
        if (event == null || !StringUtils.hasText(event.getId())) {
            throw new IllegalArgumentException("OrderUpdateEvent.id must not be null/blank");
        }
    }
}

