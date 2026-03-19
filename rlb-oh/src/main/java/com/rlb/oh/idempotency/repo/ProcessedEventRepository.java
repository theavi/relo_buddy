package com.rlb.oh.idempotency.repo;

import com.rlb.oh.idempotency.model.ProcessedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedEventRepository extends MongoRepository<ProcessedEvent, String> {
}

