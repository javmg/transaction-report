package com.jmgits.transactionreport.trx.consumer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.jmgits.transactionreport.base.utils.JsonUtils;
import com.jmgits.transactionreport.test.AbstractTest;
import com.jmgits.transactionreport.test.TransactionTestUtils;
import com.jmgits.transactionreport.trx.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

/**
 * Testing for {@link TransactionRecordConsumer}
 */
public class TransactionRecordConsumerTest extends AbstractTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testMessageValidAndTransactionCreated() throws InterruptedException {

        var originalTransaction = TransactionTestUtils.getValidTransaction();

        var id = originalTransaction.getId();

        var response = restTemplate.exchange(
            "/messages/transaction-topic",
            POST,
            new HttpEntity<>(JsonUtils.write(originalTransaction)),
            String.class
        );

        assertThat(response.getStatusCode(), is(NO_CONTENT));

        var iteration = 10;

        do {

            var savedTransaction = transactionRepository.findById(id).orElse(null);

            if (savedTransaction != null) {

                assertThat(savedTransaction, allOf(
                    hasProperty("id", is(id)),
                    hasProperty("currency", is(originalTransaction.getCurrency())),
                    hasProperty("amount", is(originalTransaction.getAmount())),
                    hasProperty("iban", is(originalTransaction.getIban())),
                    hasProperty("businessDate", is(originalTransaction.getBusinessDate())),
                    hasProperty("description", is(originalTransaction.getDescription()))
                ));

                return;
            }

            iteration--;

            Thread.sleep(100L);

        } while (iteration > 0);

        Assert.fail("Transaction should have been created by now.");
    }
}
