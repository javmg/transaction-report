package com.jmgits.transactionreport.forex.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ForexPair {

    private final String sourceCurrency;

    private final String targetCurrency;

}
