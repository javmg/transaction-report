package com.jmgits.transactionreport.test;

import static java.math.BigDecimal.TEN;
import static java.time.Month.FEBRUARY;

import com.jmgits.transactionreport.trx.domain.TransactionEntity;
import java.time.LocalDate;

public final class TransactionTestUtils {

    private TransactionTestUtils(){

    }

    public static TransactionEntity getValidTransaction() {

        var entity = new TransactionEntity();

        entity.setId("myId");
        entity.setCurrency("CHF");
        entity.setAmount(TEN);
        entity.setIban("myIban");
        entity.setBusinessDate(LocalDate.of(2021, FEBRUARY, 5));
        entity.setDescription("myDescription");

        return entity;
    }

}
