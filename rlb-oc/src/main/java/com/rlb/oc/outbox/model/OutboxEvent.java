package com.rlb.oc.outbox.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "outbox_events")
@Data
@NoArgsConstructor
public class OutboxEvent {

    @Id
    private String id;

    private OutboxEventType eventType;

    @Indexed
    private String aggregateType;

    @Indexed
    private String aggregateId;

    private String payloadJson;

    @Indexed
    private OutboxEventStatus status;

    private int attempts;

    @Indexed
    private Instant nextAttemptAt;

    private Instant createdAt;
    private Instant updatedAt;

    private String lastError;

    public OutboxEvent(
            OutboxEventType eventType,
            String aggregateType,
            String aggregateId,
            String payloadJson
    ) {
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payloadJson = payloadJson;
        this.status = OutboxEventStatus.NEW;
        this.attempts = 0;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.nextAttemptAt = this.createdAt;
    }
}

