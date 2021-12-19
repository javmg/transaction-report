package com.jmgits.transactionreport.trx.mapper;

import com.jmgits.transactionreport.customer.domain.CustomerEntity;
import com.jmgits.transactionreport.forex.client.ForexClient;
import com.jmgits.transactionreport.forex.view.ForexPair;
import com.jmgits.transactionreport.forex.view.ForexPairWithValue;
import com.jmgits.transactionreport.forex.view.ForexQuoteRequest;
import com.jmgits.transactionreport.trx.domain.AccountEntity;
import com.jmgits.transactionreport.trx.domain.TransactionEntity;
import com.jmgits.transactionreport.trx.view.AmountDto;
import com.jmgits.transactionreport.trx.view.TransactionDto;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final ForexClient forexClient;

    private final AccountMapper accountMapper;

    public Page<TransactionDto> toPageDto(CustomerEntity customer, Page<TransactionEntity> transactions, Pageable page) {

        if (transactions.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }

        var mapIbanAndAccount = customer.getAccounts().stream()
                .collect(Collectors.toMap(AccountEntity::getIban, Function.identity(), (a, b) -> a));

        var defaultCurrency = customer.getDefaultCurrency();

        var mapTargetCurrencyAndRate =
                getMapTargetCurrencyAndRate(defaultCurrency, transactions);

        var results = transactions.getContent().stream()

                .map(entity -> {

                    var dto = new TransactionDto();

                    dto.setId(entity.getId());

                    dto.setOriginalAmount(new AmountDto(entity.getCurrency(), entity.getAmount()));
                    dto.setConvertedAmount(new AmountDto(
                            defaultCurrency,
                            entity.getAmount().multiply(mapTargetCurrencyAndRate.get(entity.getCurrency())))
                    );

                    dto.setAccount(accountMapper.toDto(mapIbanAndAccount.get(entity.getIban())));

                    dto.setBusinessDate(entity.getBusinessDate());
                    dto.setDescription(entity.getDescription());

                    return dto;
                })

                .collect(Collectors.toList());

        return new PageImpl<>(results, page, transactions.getTotalElements());
    }

    //
    // private

    private Map<String, BigDecimal> getMapTargetCurrencyAndRate(String sourceCurrency, Page<TransactionEntity> transactions) {

        var targetCurrencies = transactions.stream()
                .map(TransactionEntity::getCurrency)
                .collect(Collectors.toSet());

        var request = new ForexQuoteRequest();
        request.setForexPairs(targetCurrencies.stream()
                .map(targetCurrency -> new ForexPair(sourceCurrency, targetCurrency))
                .collect(Collectors.toList()));

        var response = forexClient.getQuotes(request);

        return response.getForexPairs().stream()
                .collect(Collectors.toMap(ForexPairWithValue::getTargetCurrency, ForexPairWithValue::getValue));
    }
}
