package com.vijaystack.feign.config;

import org.springframework.context.annotation.Bean;

import com.vijaystack.feign.exception.CustomErrorDecoder;

import feign.Logger;
import feign.codec.ErrorDecoder;

public class FeignClientConfiguration {
	/*
	 * Why no @Configuration annotation on this class?
	 * 
	 * Keeping it without @Configuration prevents its beans from being picked up by
	 * the main application's component scan and accidentally applied to every Feign
	 * client.
	 */
	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}