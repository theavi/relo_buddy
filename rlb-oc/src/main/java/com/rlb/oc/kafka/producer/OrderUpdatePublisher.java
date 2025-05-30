package com.rlb.oc.kafka.producer;

import com.rlb.oc.event.OrderUpdateEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderUpdatePublisher {

    KafkaTemplate<String, OrderUpdateEvent> kafkaTemplate;

    public OrderUpdatePublisher(KafkaTemplate<String, OrderUpdateEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publishOrderUpdateEvent(OrderUpdateEvent event){
        ProducerRecord<String, OrderUpdateEvent> record = new ProducerRecord<String, OrderUpdateEvent>("order.updated.v1", event.getId().toString(), event);
        kafkaTemplate.send(record);
    }

}
