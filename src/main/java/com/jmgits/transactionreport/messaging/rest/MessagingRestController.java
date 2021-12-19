package com.jmgits.transactionreport.messaging.rest;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "application.messaging", name = "allow-creation", havingValue = "true")
public class MessagingRestController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/{topicName}")
    @ResponseStatus(NO_CONTENT)
    public void create(
        @PathVariable String topicName,
        @RequestBody String payload
    ) {
        kafkaTemplate.send(topicName, payload);
    }

}
