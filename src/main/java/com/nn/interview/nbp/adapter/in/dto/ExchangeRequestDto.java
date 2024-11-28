package com.nn.interview.nbp.adapter.in.dto;


import com.nn.interview.nbp.domain.Currency;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRequestDto {

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String userName;

    @NotNull
    private Currency fromCurrency;

    @NotNull
    private Currency toCurrency;

    @NotNull
    @DecimalMin(value = "0.0001")
    private BigDecimal amount;
}

