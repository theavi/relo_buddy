package com.rlb.oc.outbox.repo;

import com.rlb.oc.outbox.model.OutboxEvent;
import com.rlb.oc.outbox.model.OutboxEventStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface OutboxEventRepository extends MongoRepository<OutboxEvent, String> {
    List<OutboxEvent> findTop200ByStatusInAndNextAttemptAtLessThanEqualOrderByCreatedAtAsc(
            Collection<OutboxEventStatus> statuses,
            Instant now
    );
}

