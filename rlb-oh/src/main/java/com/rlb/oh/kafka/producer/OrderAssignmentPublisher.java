package com.rlb.oh.kafka.producer;

import com.rlb.oh.event.OrderAssignedToTeamEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderAssignmentPublisher {

    private KafkaTemplate<String, OrderAssignedToTeamEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.orderAssignmentTopic}")
    private String orderAssignedToTeamTopicName;

    @Autowired
    public OrderAssignmentPublisher(KafkaTemplate<String, OrderAssignedToTeamEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderAssignedToTeamEvent event){
        ProducerRecord<String, OrderAssignedToTeamEvent> record = new ProducerRecord<>(orderAssignedToTeamTopicName, event.getId().toString(), event);
        kafkaTemplate.send(record);
    }
}
