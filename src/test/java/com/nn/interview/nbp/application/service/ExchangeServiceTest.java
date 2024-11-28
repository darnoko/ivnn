package com.nn.interview.nbp.application.service;

import com.nn.interview.nbp.adapter.in.exception.ExchangeException;
import com.nn.interview.nbp.adapter.out.persistence.AccountRepository;
import com.nn.interview.nbp.adapter.out.web.ExchangeRateAdapter;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateSeriesResponseDto;
import com.nn.interview.nbp.application.service.mapper.AccountMapper;
import com.nn.interview.nbp.domain.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.nn.interview.nbp.application.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ExchangeRateAdapter ratesPort;
    @Spy
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @InjectMocks
    private ExchangeService exchangeService;


    @Test
    void testExchange_successfulPlnUsd() {
        // given
        ExchangeRateSeriesResponseDto rates = getRates();
        when(userService.getUserByUserName("testUser")).thenReturn(
                Optional.of(getUserEntity("100", "5")));
        when(ratesPort.getExchangeRates(any())).thenReturn(rates);

        // when
        exchangeService.exchange(getExchangeRequest(Currency.PLN, Currency.USD, "5"));

        // then
        verify(accountRepository, times(1)).saveAll(any());
    }

    @Test
    void testExchange_successfulUsdPln() {
        // given
        ExchangeRateSeriesResponseDto rates = getRates();
        when(userService.getUserByUserName("testUser")).thenReturn(
                Optional.of(getUserEntity("100", "5")));
        when(ratesPort.getExchangeRates(any())).thenReturn(rates);

        // when
        exchangeService.exchange(getExchangeRequest(Currency.USD, Currency.PLN, "5"));

        // then
        verify(accountRepository, times(1)).saveAll(any());
    }

    @Test
    void testExchange_failedDueToNoUser() {
        // given
        when(userService.getUserByUserName("testUser")).thenReturn(Optional.empty());

        // when
        assertThrows(ExchangeException.class,
                () -> exchangeService.exchange(getExchangeRequest(Currency.PLN, Currency.USD, "9999")));

        // then
        verify(accountRepository, never()).saveAll(any());
    }

    @Test
    void testExchange_failedDueToNoFunds() {
        // given
        when(userService.getUserByUserName("testUser"))
                .thenReturn(Optional.of(getUserEntity("1", "1")));
        when(ratesPort.getExchangeRates(any())).thenReturn(getRates());

        // when
        exchangeService.exchange(getExchangeRequest(Currency.PLN, Currency.USD, "999"));

        // then
        verify(accountRepository, never()).saveAll(any());
    }
}
