package com.jmgits.transactionreport.forex.client;

import com.jmgits.transactionreport.forex.view.ForexQuoteRequest;
import com.jmgits.transactionreport.forex.view.ForexQuoteResponse;

public interface ForexClient {

    ForexQuoteResponse getQuotes(ForexQuoteRequest request);
}
