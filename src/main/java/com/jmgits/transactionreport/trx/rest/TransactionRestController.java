package com.jmgits.transactionreport.trx.rest;

import com.jmgits.transactionreport.security.utils.SecurityUtils;
import com.jmgits.transactionreport.trx.service.TransactionService;
import com.jmgits.transactionreport.trx.view.TransactionDto;
import com.jmgits.transactionreport.trx.view.TransactionSearch;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransactionRestController {

    public final TransactionService transactionService;

    @GetMapping("/search")
    public Page<TransactionDto> search(
            @ParameterObject TransactionSearch criteria,
            @ParameterObject Pageable page
    ) {
        return transactionService.search(criteria, page, SecurityUtils.getTokenData());
    }

}
