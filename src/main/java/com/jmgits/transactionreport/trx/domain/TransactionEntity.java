package com.jmgits.transactionreport.trx.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TransactionEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "business_date", nullable = false)
    private LocalDate businessDate;

    @Column(name = "description", nullable = false)
    private String description;
}
