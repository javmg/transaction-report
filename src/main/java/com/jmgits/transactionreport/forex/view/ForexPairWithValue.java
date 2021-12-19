package com.jmgits.transactionreport.forex.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ForexPairWithValue extends ForexPair {

    private final BigDecimal value;

    public ForexPairWithValue(ForexPair forexPair, BigDecimal value) {
        super(forexPair.getSourceCurrency(), forexPair.getTargetCurrency());

        this.value = value;
    }
}
