package com.nn.interview.nbp.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebConfiguration {
    @Value("${nbp.rates.numOfRetries}")
    private int numOfRetries;
    @Value("${nbp.rates.retryTimeoutMillis}")
    private long retryTimeoutMillis;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Retry retryConfig() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(numOfRetries)
                .waitDuration(Duration.ofMillis(retryTimeoutMillis))
                .build();
        RetryRegistry registry = RetryRegistry.of(config);
        return registry.retry("apiRetry");
    }
}
