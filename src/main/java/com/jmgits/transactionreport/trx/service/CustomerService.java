package com.jmgits.transactionreport.trx.service;

import com.jmgits.transactionreport.trx.view.CustomerDto;

public interface CustomerService {

    CustomerDto getById(String id);
}
