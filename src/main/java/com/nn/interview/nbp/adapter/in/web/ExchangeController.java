package com.nn.interview.nbp.adapter.in.web;

import com.nn.interview.nbp.adapter.in.dto.ExchangeRequestDto;
import com.nn.interview.nbp.application.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PSource;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@Validated
public class ExchangeController {
    private final ExchangeService exchangeService;

    @PostMapping()
    public ResponseEntity<Void> exchangeCurrency(@Validated @RequestBody ExchangeRequestDto exchangeDto) {
        exchangeService.exchange(exchangeDto);
        return ResponseEntity.ok().build();
    }
}

