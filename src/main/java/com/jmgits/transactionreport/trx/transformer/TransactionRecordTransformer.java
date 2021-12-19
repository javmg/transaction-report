package com.jmgits.transactionreport.trx.transformer;

import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.jmgits.transactionreport.base.utils.JsonUtils;
import com.jmgits.transactionreport.trx.domain.TransactionEntity;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class TransactionRecordTransformer {

    public Optional<TransactionEntity> transform(ConsumerRecord<?, String> record) {
        return transform(record.value());
    }

    public Optional<TransactionEntity> transform(String recordValue) {

        try {

            return Optional.of(transformInternal(recordValue));

        } catch (TransactionInvalidException exception) {

            log.error("Error transforming transaction: {} with reason: {}",
                recordValue,
                exception.getMessage()
            );
        }

        return empty();
    }

    //
    // private

    private TransactionEntity transformInternal(String recordValue) {

        TransactionEntity transaction;

        try {

            transaction = JsonUtils.read(recordValue, TransactionEntity.class);

        } catch (Exception exception) {

            throw new TransactionInvalidException(
                "Invalid message."
            );
        }

        if (isInvalid(transaction.getId(), 36)) {
            throw new TransactionInvalidException(
                "Id cannot be empty and must be less than 36 characters."
            );
        }

        if (isInvalid(transaction.getCurrency(), 3)) {
            throw new TransactionInvalidException(
                "Currency cannot be empty and must be less than 3 characters."
            );
        }

        if (transaction.getAmount() == null) {
            throw new TransactionInvalidException(
                "Amount cannot be empty."
            );
        }

        if (isInvalid(transaction.getIban(), 256)) {
            throw new TransactionInvalidException(
                "IBAN cannot be empty and must be less than 256 characters."
            );
        }

        if (transaction.getBusinessDate() == null) {
            throw new TransactionInvalidException(
                "Business date cannot be empty."
            );
        }

        if (isInvalid(transaction.getDescription(), 256)) {
            throw new TransactionInvalidException(
                "Description cannot be empty and must be less than 256 characters."
            );
        }

        return transaction;
    }

    private boolean isInvalid(String value, int maxLength) {
        return isBlank(value) || value.length() > maxLength;
    }
    
    private static class TransactionInvalidException extends RuntimeException{
        
        public TransactionInvalidException(String message){
            super(message);
        } 
    }

}
