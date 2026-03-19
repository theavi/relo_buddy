package com.rlb.oh.consumer;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.idempotency.model.ProcessedEvent;
import com.rlb.oh.idempotency.repo.ProcessedEventRepository;
import com.rlb.oh.service.OrderHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Objects;

@Service
public class OrderCreatedEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreateEventConsumer.class);

    private final CreateOrderEventProcessor createOrderEventProcessor;

    public OrderCreatedEventListener(CreateOrderEventProcessor createOrderEventProcessor) {
        this.createOrderEventProcessor = createOrderEventProcessor;
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.orderCreated:order.created.v1}",
            groupId = "${spring.kafka.consumer.group-id:rlbGroup}"
    )
    public void consume(
            @Payload OrderCreateEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) {
        if (event == null) {
            logger.error("Received null OrderCreateEvent. topic='{}', partition={}, offset={}", topic, partition, offset);
            throw new InvalidOrderEventException("Null event received");
        }

        final String rawId = event.getId();
        if (!StringUtils.hasText(rawId)) {
            logger.error("Missing eventId in OrderCreateEvent. topic='{}', partition={}, offset={}", topic, partition, offset);
            throw new InvalidOrderEventException("Event has blank/null id");
        }

        final String eventId = rawId.trim();
        logger.debug("OrderCreateEvent received. eventId='{}', topic='{}', partition={}, offset={}",
                eventId, topic, partition, offset);

        createOrderEventProcessor.processIdempotently(event, eventId, topic, partition, offset);
    }

}
