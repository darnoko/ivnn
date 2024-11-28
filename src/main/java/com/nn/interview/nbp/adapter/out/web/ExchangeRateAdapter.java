package com.nn.interview.nbp.adapter.out.web;

import com.nn.interview.nbp.adapter.out.ExchangeRatePort;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateSeriesResponseDto;
import com.nn.interview.nbp.domain.Currency;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ExchangeRateAdapter implements ExchangeRatePort {
    private final RestTemplate restTemplate;
    private final Retry apiRetry;
    @Value("${nbp.rates.url}")
    private String url;


    @io.github.resilience4j.retry.annotation.Retry(name = "apiRetry")
    private ExchangeRateSeriesResponseDto exchangeRates(Currency currencyCode) {
        var uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(Currency.USD)
                .toUriString();

        return apiRetry.executeSupplier(() -> restTemplate.getForObject(uri, ExchangeRateSeriesResponseDto.class));
    }

    @Override
    public ExchangeRateSeriesResponseDto getExchangeRates(Currency currencyCode) {
        return exchangeRates(currencyCode);
    }
}

