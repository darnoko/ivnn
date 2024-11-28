package com.nn.interview.nbp.adapter.in.dto;


import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
public class AccountsResponseDto {
    List<AccountDto> accounts;
}

