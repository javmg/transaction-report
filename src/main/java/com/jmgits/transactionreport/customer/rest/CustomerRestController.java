package com.jmgits.transactionreport.customer.rest;

import com.jmgits.transactionreport.security.utils.SecurityUtils;
import com.jmgits.transactionreport.trx.service.CustomerService;
import com.jmgits.transactionreport.trx.view.CustomerDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping("/me")
    public CustomerDto getMe() {

        var userId = SecurityUtils.getUserId();

        return customerService.getById(userId);
    }
}
