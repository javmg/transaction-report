package com.jmgits.transactionreport.forex.client.impl;

import com.jmgits.transactionreport.forex.client.ForexClient;
import com.jmgits.transactionreport.forex.view.ForexQuoteRequest;
import com.jmgits.transactionreport.forex.view.ForexQuoteResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "application.forex", name = "type", havingValue = "http")
public class HttpForexClient implements ForexClient {

    @Override
    public ForexQuoteResponse getQuotes(ForexQuoteRequest request) {
        throw new RuntimeException("To be implemented");
    }
}
