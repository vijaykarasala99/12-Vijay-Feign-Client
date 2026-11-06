package com.vijaystack.feign.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@Configuration
public class CircuitBreakerConfiguration {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCircuitBreakerConfiguration(
			@Value("${resilience.user-service.failure-rate-threshold:50}") float failureRateThreshold,
			@Value("${resilience.user-service.sliding-window-size:20}") int slidingWindowSize,
			@Value("${resilience.user-service.minimum-number-of-calls:10}") int minimumNumberOfCalls,
			@Value("${resilience.user-service.wait-duration-in-open-state:20s}") Duration waitDuration) {

		return factory -> factory
				.configureDefault(id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig
						.custom().failureRateThreshold(failureRateThreshold).slidingWindowSize(slidingWindowSize)
						.minimumNumberOfCalls(minimumNumberOfCalls).waitDurationInOpenState(waitDuration).build())
						.build());
	}
}