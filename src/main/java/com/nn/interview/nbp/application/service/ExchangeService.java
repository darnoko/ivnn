package com.nn.interview.nbp.application.service;

import com.nn.interview.nbp.adapter.in.dto.ExchangeRequestDto;
import com.nn.interview.nbp.adapter.in.exception.ExchangeException;
import com.nn.interview.nbp.adapter.out.persistence.AccountRepository;
import com.nn.interview.nbp.adapter.out.persistence.entity.AccountEntity;
import com.nn.interview.nbp.adapter.out.persistence.entity.UserEntity;
import com.nn.interview.nbp.adapter.out.web.ExchangeRateAdapter;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateDto;
import com.nn.interview.nbp.adapter.out.web.dto.ExchangeRateSeriesResponseDto;
import com.nn.interview.nbp.application.service.mapper.AccountMapper;
import com.nn.interview.nbp.domain.Account;
import com.nn.interview.nbp.domain.Currency;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final ExchangeRateAdapter ratesPort;
    private final AccountMapper accountMapper;

    @Transactional
    public void exchange(@Validated ExchangeRequestDto exchangeRequestDto) {
        userService.getUserByUserName(exchangeRequestDto.getUserName())
                .stream()
                .filter(u -> exchangeRequestDto.getFromCurrency() != exchangeRequestDto.getToCurrency())
                .map(u -> ratesPort.getExchangeRates(exchangeRequestDto.getFromCurrency()))
                .peek(r -> exchangeCurrencies(exchangeRequestDto, r))
                .findFirst()
                .orElseThrow(() -> new ExchangeException("Unable to process exchange transaction."));
    }

    private void exchangeCurrencies(ExchangeRequestDto exchangeRequestDto, ExchangeRateSeriesResponseDto rates) {
        var accounts = getAccounts(exchangeRequestDto);

        var accountFrom = getAccountByCurrency(accounts, exchangeRequestDto.getFromCurrency());
        var accountTo = getAccountByCurrency(accounts, exchangeRequestDto.getToCurrency());

        var currentRate = rates.getRates().stream().findFirst().map(ExchangeRateDto::getMid).map(BigDecimal::new).orElseThrow();
        var exchangeRate = exchangeRequestDto.getFromCurrency() == Currency.PLN ? currentRate : BigDecimal.ONE.divide(currentRate, 4, RoundingMode.HALF_UP);

        if (accountFrom.getBalance().compareTo(exchangeRequestDto.getAmount()) >= 0) {
            accountFrom.setBalance(accountFrom.getBalance().subtract(exchangeRequestDto.getAmount()));
            accountTo.setBalance(accountTo.getBalance().add(exchangeRequestDto.getAmount().divide(exchangeRate, 4, RoundingMode.HALF_UP)));
            accountRepository.saveAll(List.of(accountMapper.toAccountEntity(accountTo), accountMapper.toAccountEntity(accountFrom)));
        }

    }

    private Account getAccountByCurrency(List<AccountEntity> accounts, Currency currency) {
        return accounts.stream()
                .filter(a -> a.getCurrency() == currency)
                .findFirst()
                .map(accountMapper::toAccount)
                .orElseThrow();
    }

    private List<AccountEntity> getAccounts(ExchangeRequestDto exchangeRequestDto) {
        return userService.getUserByUserName(exchangeRequestDto.getUserName()).map(UserEntity::getAccounts).orElseThrow(() -> new ExchangeException("Unable to get user accounts."));
    }
}
