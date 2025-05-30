package com.rlb.oh.config;

import com.rlb.oc.event.OrderCreateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreateEvent> kafkaListenerContainerFactory(ConsumerFactory<String, OrderCreateEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreateEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(
                new DefaultErrorHandler(
                        new FixedBackOff(2000, 3)
                )
        );
        return factory;
    }
}
