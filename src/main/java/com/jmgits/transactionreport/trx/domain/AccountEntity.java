package com.jmgits.transactionreport.trx.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AccountEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "iban", nullable = false, unique = true)
    private String iban;

    @Column(name = "bic", nullable = false)
    private String bic;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_address", nullable = false)
    private String bankAddress;
}
