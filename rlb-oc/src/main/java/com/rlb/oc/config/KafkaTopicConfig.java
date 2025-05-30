package com.rlb.oc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Objects;

@Configuration
public class KafkaTopicConfig {

    @Autowired
    private Environment env;

    @Bean
    NewTopic userCreatedTopic(){
        String topicName = env.getProperty("spring.kafka.topics.orderCreated");
        Objects.requireNonNull(topicName, "Kafka topic 'orderCreated' must be set in application.yml");
        return TopicBuilder.name(topicName).partitions(3).build();
    }

    @Bean
    NewTopic orderUpdatedTopic() {
        String topicName = env.getProperty("spring.kafka.topics.orderUpdated");
        Objects.requireNonNull(topicName, "Kafka topic 'orderUpdated' must be set in application.yml");
        return TopicBuilder.name(topicName).partitions(3).build();
    }
}
