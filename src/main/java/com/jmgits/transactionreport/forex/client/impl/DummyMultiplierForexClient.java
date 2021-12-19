package com.jmgits.transactionreport.forex.client.impl;

import com.jmgits.transactionreport.forex.client.ForexClient;
import com.jmgits.transactionreport.forex.view.ForexPairWithValue;
import com.jmgits.transactionreport.forex.view.ForexQuoteRequest;
import com.jmgits.transactionreport.forex.view.ForexQuoteResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@ConditionalOnProperty(prefix = "application.forex", name = "type", havingValue = "dummy")
public class DummyMultiplierForexClient implements ForexClient {

    @Override
    public ForexQuoteResponse getQuotes(ForexQuoteRequest request) {

        var response = new ForexQuoteResponse();

        if (isEmpty(request.getForexPairs())) {
            response.setForexPairs(new ArrayList<>());

        } else {
            response.setForexPairs(request.getForexPairs().stream()
                    .map(pair -> new ForexPairWithValue(
                            pair,
                            pair.getSourceCurrency().equals(pair.getTargetCurrency()) ? ONE : TEN
                    ))
                    .collect(Collectors.toList())
            );
        }

        return response;
    }
}
