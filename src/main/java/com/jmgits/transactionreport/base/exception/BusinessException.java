package com.jmgits.transactionreport.base.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
@Getter
public class BusinessException extends GeneralException {

    public BusinessException(String code, String description) {
        super(code, description);
    }
}
