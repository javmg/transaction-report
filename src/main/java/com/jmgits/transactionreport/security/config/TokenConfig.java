package com.jmgits.transactionreport.security.config;

import com.jmgits.transactionreport.security.resolver.TokenHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean
    public TokenHandler tokenHandler(@Value("${application.token.secret}") String secret) {
        return new TokenHandler(secret);
    }
}
