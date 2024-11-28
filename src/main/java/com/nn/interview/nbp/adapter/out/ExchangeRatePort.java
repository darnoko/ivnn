package com.nn.interview.nbp.adapter.out;

import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateSeriesResponseDto;
import com.nn.interview.nbp.domain.Currency;

public interface ExchangeRatePort {
    public ExchangeRateSeriesResponseDto getExchangeRates(Currency currencyCode);
}
