package com.nn.interview.nbp.application;

import com.nn.interview.nbp.adapter.in.dto.ExchangeRequestDto;
import com.nn.interview.nbp.adapter.out.persistence.entity.AccountEntity;
import com.nn.interview.nbp.adapter.out.persistence.entity.UserEntity;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateDto;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateSeriesResponseDto;
import com.nn.interview.nbp.domain.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.nn.interview.nbp.application.service.AccountService.SCALE_4;

public class TestFactory {
    public static ExchangeRequestDto getExchangeRequest(Currency fromCurrency, Currency toCurrency,
                                                        String amount) {
        return ExchangeRequestDto.builder()
                .userName("testUser")
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .amount(new BigDecimal(amount).setScale(SCALE_4, RoundingMode.HALF_UP))
                .build();
    }

    public static UserEntity getUserEntity(String amountPln, String amountUsd) {
        return UserEntity.builder()
                .accounts(List.of(
                        getAccount(Currency.PLN, amountPln),
                        getAccount(Currency.USD, amountUsd)))
                .build();
    }

    public static AccountEntity getAccount(Currency currency, String balance) {
        return AccountEntity.builder()
                .currency(currency)
                .balance(new BigDecimal(balance).setScale(SCALE_4, RoundingMode.HALF_UP))
                .build();
    }

    public static ExchangeRateSeriesResponseDto getRates() {
        return ExchangeRateSeriesResponseDto.builder()
                .rates(List.of(ExchangeRateDto.builder().mid("2.0000").build())).build();
    }
}
