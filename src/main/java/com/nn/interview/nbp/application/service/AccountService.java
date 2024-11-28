package com.nn.interview.nbp.application.service;

import com.nn.interview.nbp.adapter.in.dto.AccountsResponseDto;
import com.nn.interview.nbp.adapter.in.exception.AccountException;
import com.nn.interview.nbp.adapter.out.persistence.entity.UserEntity;
import com.nn.interview.nbp.application.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserService userService;
    private final AccountMapper accountMapper;
    public static final int SCALE_4 = 4;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public AccountsResponseDto getUserAccounts(String userName) {
        return userService.getUserByUserName(userName)
                .map(UserEntity::getAccounts)
                .map(accounts -> accounts.stream()
                        .map(accountMapper::toAccount)
                        .map(accountMapper::toAccountDto)
                        .toList())
                .map(AccountsResponseDto::new)
                .orElseThrow(() -> new AccountException("Error while loading accounts."));
    }
}
