package com.jmgits.transactionreport.trx.service.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.jmgits.transactionreport.base.exception.BusinessException;
import com.jmgits.transactionreport.base.exception.NotFoundException;
import com.jmgits.transactionreport.customer.domain.CustomerEntity;
import com.jmgits.transactionreport.customer.repository.CustomerRepository;
import com.jmgits.transactionreport.security.view.TokenData;
import com.jmgits.transactionreport.trx.domain.AccountEntity;
import com.jmgits.transactionreport.trx.mapper.TransactionMapper;
import com.jmgits.transactionreport.trx.repository.TransactionRepository;
import com.jmgits.transactionreport.trx.service.TransactionService;
import com.jmgits.transactionreport.trx.view.TransactionDto;
import com.jmgits.transactionreport.trx.view.TransactionSearch;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDto> search(TransactionSearch criteria, Pageable page, TokenData token) {

        var customer = customerRepository.getById(token.getUserId());

        sanitizeAccountIbans(customer, criteria);

        var entities = transactionRepository.search(criteria, page);

        return transactionMapper.toPageDto(customer, entities, page);
    }

    //
    // private

    private void sanitizeAccountIbans(CustomerEntity customer, TransactionSearch criteria) {

        var customerAccounts = customer.getAccounts();

        if (customerAccounts.isEmpty()) {
            throw new BusinessException("CUSTOMER_WITHOUT_ACCOUNTS", "This customer has no accounts.");
        }

        var passedIbans = criteria.getAccountIbans();

        criteria.setAccountIbans(isEmpty(passedIbans) ?

                customerAccounts.stream()
                        .map(AccountEntity::getIban)
                        .collect(Collectors.toSet()) :

                passedIbans.stream()
                        .map(passedAccountIban -> customerAccounts.stream()
                                .map(AccountEntity::getIban)
                                .filter(customerAccountIban -> customerAccountIban.equalsIgnoreCase(passedAccountIban))
                                .findFirst()
                                .orElseThrow(() -> new NotFoundException("ACCOUNT_NOT_FOUND", String.format("Account with IBAN '%s' not found.", passedAccountIban)))
                        )
                        .collect(Collectors.toSet())
        );
    }

}
