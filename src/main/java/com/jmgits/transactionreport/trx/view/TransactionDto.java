package com.jmgits.transactionreport.trx.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {

    private String id;

    private AmountDto originalAmount;

    private AmountDto convertedAmount;

    private AccountDto account;

    private LocalDate businessDate;

    private String description;
}
