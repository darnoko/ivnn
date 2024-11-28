package com.nn.interview.nbp.adapter.in.dto;


import com.nn.interview.nbp.domain.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDto {
    Currency currency;
    BigDecimal balance;
}
