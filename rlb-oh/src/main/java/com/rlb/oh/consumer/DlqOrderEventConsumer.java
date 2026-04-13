package com.rlb.oh.consumer;

import com.rlb.oh.dlq.event.DlqEventContext;
import com.rlb.oh.dlq.service.DlqEventService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DlqOrderEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DlqOrderEventConsumer.class);

    private final DlqEventService dlqEventService;

    public DlqOrderEventConsumer(DlqEventService dlqEventService) {
        this.dlqEventService = dlqEventService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.dlq:order.created.v1.DLQ}",
            groupId = "${spring.kafka.consumer.group-id:rlbGroup}-dlq"
    )
    public void consumeDlq(
            ConsumerRecord<String, Object> record,
            @Header(value = KafkaHeaders.EXCEPTION_MESSAGE,   required = false) String exceptionMessage,
            @Header(value = KafkaHeaders.EXCEPTION_FQCN,      required = false) String exceptionClass,
            @Header(value = KafkaHeaders.EXCEPTION_STACKTRACE,required = false) String stackTrace,
            @Header(value = KafkaHeaders.ORIGINAL_TOPIC,      required = false) String originalTopic,
            @Header(value = KafkaHeaders.ORIGINAL_PARTITION,  required = false) Integer originalPartition,
            @Header(value = KafkaHeaders.ORIGINAL_OFFSET,     required = false) Long originalOffset,
            @Header(value = KafkaHeaders.ORIGINAL_TIMESTAMP,  required = false) Long originalTimestamp
    ) {
        logger.error(
            "DLQ message received. originalTopic='{}', originalPartition={}, originalOffset={}, " +
            "exceptionClass='{}', exceptionMessage='{}'",
            originalTopic, originalPartition, originalOffset,
            exceptionClass, exceptionMessage
        );

        DlqEventContext context = DlqEventContext.builder()
                .payload(record.value())
                .originalTopic(originalTopic)
                .originalPartition(originalPartition)
                .originalOffset(originalOffset)
                .originalTimestamp(originalTimestamp != null
                        ? Instant.ofEpochMilli(originalTimestamp) : null)
                .exceptionClass(exceptionClass)
                .exceptionMessage(exceptionMessage)
                .stackTrace(stackTrace)
                .dlqReceivedAt(Instant.now())
                .build();

        dlqEventService.handle(context);
    }
}