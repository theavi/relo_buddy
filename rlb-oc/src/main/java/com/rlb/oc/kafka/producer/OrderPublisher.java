package com.rlb.oc.kafka.producer;

import com.rlb.oc.event.OrderCreateEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private KafkaTemplate<String, OrderCreateEvent> kafkaTemplate;

    public OrderPublisher(KafkaTemplate<String, OrderCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderPlaceEvent(OrderCreateEvent event) {
        ProducerRecord<String, OrderCreateEvent> record = new ProducerRecord<String, OrderCreateEvent>("order.created.v1", event.getId().toString(), event);
        kafkaTemplate.send(record);
    }
}
