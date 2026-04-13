package com.rlb.oh.idempotency.service;

import com.rlb.oh.idempotency.model.ProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class IdempotencyGuard {

    private static final Logger logger = LoggerFactory.getLogger(IdempotencyGuard.class);

    private final MongoTemplate mongoTemplate;

    public IdempotencyGuard(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean tryClaimEvent(String eventId, String topic,
                                  Integer partition, Long offset) {
        try {
            Query query = Query.query(Criteria.where("_id").is(eventId));

            Update update = new Update()
                    .setOnInsert("_id",          eventId)
                    .setOnInsert("processed_at", Instant.now())
                    .setOnInsert("topic",         topic)
                    .setOnInsert("partition",     partition)
                    .setOnInsert("offset",        offset);

            FindAndModifyOptions options = FindAndModifyOptions.options()
                    .upsert(true)      
                    .returnNew(false); 

            ProcessedEvent existing = mongoTemplate.findAndModify(
                    query, update, options, ProcessedEvent.class
            );

            if (existing != null) {
                logger.info("Duplicate event skipped. eventId='{}', topic='{}', partition={}, offset={}",
                        eventId, topic, partition, offset);
                return false;
            }

            logger.info("Event claimed. eventId='{}'", eventId);
            return true;

        } catch (DuplicateKeyException ex) {
            logger.info("Concurrent duplicate detected. eventId='{}' — skipping.", eventId);
            return false;
        }
    }

    public void releaseClaimEvent(String eventId) {
        mongoTemplate.remove(
                Query.query(Criteria.where("_id").is(eventId)),
                ProcessedEvent.class
        );
        logger.warn("Claim released after failure. eventId='{}' will be retried.", eventId);
    }
}