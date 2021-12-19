package com.jmgits.transactionreport.trx.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Getter
@Setter
public class TransactionSearch {

    private Set<String> accountIbans;

    @DateTimeFormat(iso = DATE)
    private LocalDate businessDateFrom;

    @DateTimeFormat(iso = DATE)
    private LocalDate businessDateTo;
}
