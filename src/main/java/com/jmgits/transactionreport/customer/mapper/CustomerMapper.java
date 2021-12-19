package com.jmgits.transactionreport.customer.mapper;

import com.jmgits.transactionreport.customer.domain.CustomerEntity;
import com.jmgits.transactionreport.trx.view.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toDto(CustomerEntity entity) {

        var dto = new CustomerDto();

        dto.setId(entity.getId());
        dto.setDefaultCurrency(entity.getDefaultCurrency());

        return dto;
    }

}
