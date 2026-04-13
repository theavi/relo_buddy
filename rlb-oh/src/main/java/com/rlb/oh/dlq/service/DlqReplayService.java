package com.rlb.oh.dlq.service;

import com.rlb.oh.dlq.entity.DlqEvent;
import com.rlb.oh.dlq.entity.DlqEventStatus;
import com.rlb.oh.dlq.repository.DlqEventRepository;
import com.rlb.oh.idempotency.service.CreateOrderEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DlqReplayService {

    private static final Logger logger = LoggerFactory.getLogger(DlqReplayService.class);

    private final DlqEventRepository dlqEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CreateOrderEventProcessor createOrderEventProcessor;

    @Value("${spring.kafka.topics.orderCreated:order.created.v1}")
    private String orderCreatedTopic;

    public DlqReplayService(DlqEventRepository dlqEventRepository,
                             KafkaTemplate<String, Object> kafkaTemplate,
                            CreateOrderEventProcessor createOrderEventProcessor) {
        this.dlqEventRepository  = dlqEventRepository;
        this.kafkaTemplate       = kafkaTemplate;
        this.createOrderEventProcessor = createOrderEventProcessor;
    }

    @Transactional
    public void replay(Long dlqEventId, String remarks) {
        DlqEvent dlqEvent = dlqEventRepository.findById(String.valueOf(dlqEventId))
                .orElseThrow(() -> new IllegalArgumentException(
                        "DLQ event not found. id=" + dlqEventId));

        if (dlqEvent.getStatus() != DlqEventStatus.PENDING) {
            throw new IllegalStateException(
                    "DLQ event is not in PENDING state. id=" + dlqEventId +
                    ", status=" + dlqEvent.getStatus());
        }

        // Republish to original topic — will be picked up by normal consumer
        kafkaTemplate.send(orderCreatedTopic, dlqEvent.getPayload());
        logger.info("DLQ event replayed to topic='{}'. dlqEventId='{}'",
                orderCreatedTopic, dlqEventId);

        dlqEvent.markReplayed(remarks);
        dlqEventRepository.save(dlqEvent);
    }

    /**
     * Discards a DLQ event — operator confirmed it can be ignored.
     */
    @Transactional
    public void discard(Long dlqEventId, String remarks) {
        DlqEvent dlqEvent = dlqEventRepository.findById(String.valueOf(dlqEventId))
                .orElseThrow(() -> new IllegalArgumentException(
                        "DLQ event not found. id=" + dlqEventId));

        if (dlqEvent.getStatus() != DlqEventStatus.PENDING) {
            throw new IllegalStateException(
                    "DLQ event is not in PENDING state. id=" + dlqEventId);
        }

        dlqEvent.markDiscarded(remarks);
        dlqEventRepository.save(dlqEvent);
        logger.info("DLQ event discarded. dlqEventId='{}', remarks='{}'", dlqEventId, remarks);
    }

    /**
     * Returns all PENDING DLQ events — for ops dashboard.
     */
    public List<DlqEvent> getPendingEvents() {
        return dlqEventRepository.findByStatus(DlqEventStatus.PENDING);
    }
}