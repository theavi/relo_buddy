package com.rlb.oc.kafka.producer;

import com.rlb.oc.event.OrderUpdateEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderUpdatePublisher {

    KafkaTemplate<String, OrderUpdateEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.orderUpdated}")
    private String orderUpdatedTopicName;

    @Autowired
    public OrderUpdatePublisher(KafkaTemplate<String, OrderUpdateEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publishOrderUpdateEvent(OrderUpdateEvent event){
        ProducerRecord<String, OrderUpdateEvent> record =
                new ProducerRecord<String, OrderUpdateEvent>(orderUpdatedTopicName, event.getId().toString(), event);
        kafkaTemplate.send(record);
    }

}
