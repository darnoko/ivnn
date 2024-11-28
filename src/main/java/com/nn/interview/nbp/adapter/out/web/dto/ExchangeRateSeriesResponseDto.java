package com.nn.interview.nbp.adapter.out.web.dto;

import com.nn.interview.nbp.domain.Currency;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExchangeRateSeriesResponseDto {
    private String table;
    private String currency;
    private Currency code;

    private List<ExchangeRateDto> rates;
}
