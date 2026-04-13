package com.rlb.oh.idempotency.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "processed_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedEvent {

    @Id
    private String eventId;

    @Field("processed_at")
    private Instant processedAt;

    @Field("topic")
    private String topic;

    @Field("partition")
    private Integer partition;

    @Field("offset")
    private Long offset;
}