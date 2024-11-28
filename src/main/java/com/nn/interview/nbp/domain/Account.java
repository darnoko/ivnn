package com.nn.interview.nbp.domain;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Account {
    private Long id;
    private Currency currency;
    private BigDecimal balance;
}
