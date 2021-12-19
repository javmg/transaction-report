package com.jmgits.transactionreport.security.rest;

import com.jmgits.transactionreport.security.resolver.TokenHandler;
import com.jmgits.transactionreport.security.view.TokenRequest;
import com.jmgits.transactionreport.security.view.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "application.token", name = "allow-creation", havingValue = "true")
public class TokenRestController {

    public final TokenHandler tokenHandler;

    @PostMapping
    public TokenResponse createToken(@RequestBody TokenRequest criteria) {

        var token = tokenHandler.generateToken(criteria.getUserId());

        return new TokenResponse(token);
    }
}
