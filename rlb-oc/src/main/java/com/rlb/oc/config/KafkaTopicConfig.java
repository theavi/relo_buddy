package com.rlb.oc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic userCreatedTopic(){
        return TopicBuilder.name("order.created.v1").partitions(3).build();
    }
}
