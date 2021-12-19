package com.jmgits.transactionreport.trx.consumer;

import com.jmgits.transactionreport.trx.repository.TransactionRepository;
import com.jmgits.transactionreport.trx.transformer.TransactionRecordTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionRecordConsumer {

    private final TransactionRecordTransformer transformer = new TransactionRecordTransformer();

    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "${application.transaction.topic.name}")
    public void receive(ConsumerRecord<?, String> consumerRecord) {

        log.debug("Received payload: '{}'", consumerRecord.toString());

        var transaction = transformer.transform(consumerRecord).orElse(null);

        if (transaction == null) {
            return;
        }

        var id = transaction.getId();

        if (transactionRepository.existsById(id)) {
            log.warn("Transaction with id '{}' existed already", id);
            return;
        }

        transactionRepository.save(transaction);
    }
}
