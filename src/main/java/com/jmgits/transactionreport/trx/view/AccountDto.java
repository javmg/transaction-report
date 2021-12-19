package com.jmgits.transactionreport.trx.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    private String id;

    private String iban;

    private String bic;

    private String bankName;

    private String bankAddress;
}
