package com.jmgits.transactionreport.security.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRequest {

    @NotBlank
    private String userId;
}
