package com.jmgits.transactionreport.customer.repository;

import com.jmgits.transactionreport.base.exception.NotFoundException;
import com.jmgits.transactionreport.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    default CustomerEntity getById(String id) {

        return findById(id).orElseThrow(() -> new NotFoundException(
                "CUSTOMER_NOT_FOUND",
                String.format("Customer with id '%s' not found.", id)
        ));
    }

}
