package com.rlb.oc.kafka.producer;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.OrderUpdateEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private KafkaTemplate<String, OrderCreateEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.orderCreated}")
    private String orderCreatedTopicName;

    @Autowired
    public OrderPublisher(KafkaTemplate<String, OrderCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderPlaceEvent(OrderCreateEvent event) {
        ProducerRecord<String, OrderCreateEvent> record = new ProducerRecord<String, OrderCreateEvent>(orderCreatedTopicName, event.getId().toString(), event);
        kafkaTemplate.send(record);
    }
}
