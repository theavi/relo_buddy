package com.rlb.oh.consumer;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.exception.InvalidOrderEventException;
import com.rlb.oh.idempotency.service.CreateOrderEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrderCreateEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreateEventConsumer.class);

    private final CreateOrderEventProcessor createOrderEventProcessor;

    public OrderCreateEventConsumer(CreateOrderEventProcessor createOrderEventProcessor) {
        this.createOrderEventProcessor = createOrderEventProcessor;
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.orderCreated:order.created.v1}",
            groupId = "${spring.kafka.consumer.group-id:rlbGroup}",
            errorHandler = "globalKafkaExceptionHandler"  // ← wired here
    )
    public void consume(
            @Payload OrderCreateEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) {
        if (event == null) {
            logger.error("Null event received. topic='{}', partition={}, offset={}",
                    topic, partition, offset);
            throw new InvalidOrderEventException("Null event received");
        }

        final String rawId = event.getId();
        if (!StringUtils.hasText(rawId)) {
            logger.error("Blank eventId received. topic='{}', partition={}, offset={}",
                    topic, partition, offset);
            throw new InvalidOrderEventException("Event has blank/null id");
        }

        final String eventId = rawId.trim();
        logger.debug("Event received. eventId='{}', topic='{}', partition={}, offset={}",
                eventId, topic, partition, offset);

        createOrderEventProcessor.processIdempotently(event, eventId, topic, partition, offset);
    }
}