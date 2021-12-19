package com.jmgits.transactionreport.forex.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ForexQuoteRequest {

    private List<ForexPair> forexPairs;
}
