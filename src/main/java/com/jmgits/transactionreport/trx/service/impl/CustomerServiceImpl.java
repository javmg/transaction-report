package com.jmgits.transactionreport.trx.service.impl;

import com.jmgits.transactionreport.customer.mapper.CustomerMapper;
import com.jmgits.transactionreport.customer.repository.CustomerRepository;
import com.jmgits.transactionreport.trx.service.CustomerService;
import com.jmgits.transactionreport.trx.view.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getById(String id) {

        var customer = customerRepository.getById(id);

        return customerMapper.toDto(customer);
    }
}
