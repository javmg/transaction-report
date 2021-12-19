package com.jmgits.transactionreport.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
public class KafkaConfig {

    @Bean
    public EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaBroker(1)
            .kafkaPorts(9092)
            .brokerListProperty("spring.kafka.bootstrap-servers");
    }
}
