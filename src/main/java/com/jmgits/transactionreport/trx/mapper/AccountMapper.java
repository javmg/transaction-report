package com.jmgits.transactionreport.trx.mapper;

import com.jmgits.transactionreport.trx.domain.AccountEntity;
import com.jmgits.transactionreport.trx.view.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto toDto(AccountEntity entity) {

        var dto = new AccountDto();

        dto.setId(entity.getId());
        dto.setIban(entity.getIban());
        dto.setBic(entity.getBic());
        dto.setBankName(entity.getBankName());
        dto.setBankAddress(entity.getBankAddress());

        return dto;
    }

}
