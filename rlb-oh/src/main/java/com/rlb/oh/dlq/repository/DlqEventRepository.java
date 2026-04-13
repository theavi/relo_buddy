package com.rlb.oh.dlq.repository;

import com.rlb.oh.dlq.entity.DlqEvent;
import com.rlb.oh.dlq.entity.DlqEventStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DlqEventRepository extends MongoRepository<DlqEvent, String> {

    List<DlqEvent> findByStatus(DlqEventStatus status);

    List<DlqEvent> findByOriginalTopicAndStatus(String originalTopic, DlqEventStatus status);

    long countByStatus(DlqEventStatus status);
}