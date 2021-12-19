package com.jmgits.transactionreport.trx.service;

import com.jmgits.transactionreport.security.view.TokenData;
import com.jmgits.transactionreport.trx.view.TransactionDto;
import com.jmgits.transactionreport.trx.view.TransactionSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Page<TransactionDto> search(TransactionSearch criteria, Pageable page, TokenData token);
}
