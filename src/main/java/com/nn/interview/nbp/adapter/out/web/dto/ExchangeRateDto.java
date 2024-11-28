package com.nn.interview.nbp.adapter.out.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateDto {
    private String no;
    private String effectiveDate;
    private String mid;
}
