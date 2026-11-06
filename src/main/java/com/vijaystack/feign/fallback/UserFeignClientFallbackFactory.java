package com.vijaystack.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.vijaystack.feign.client.UserFeignClient;
import com.vijaystack.feign.dto.UserRequest;
import com.vijaystack.feign.dto.UserResponse;
import com.vijaystack.feign.exception.DownstreamResourceNotFoundException;
import com.vijaystack.feign.exception.DownstreamServiceException;

import feign.FeignException;

@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

	private static final Logger log = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

	@Override
	public UserFeignClient create(Throwable cause) {

		if (cause instanceof DownstreamResourceNotFoundException notFoundException) {
			throw notFoundException;
		}

		if (cause instanceof FeignException feignException && feignException.status() >= 400
				&& feignException.status() < 500) {
			throw feignException;
		}

		log.error("User service communication failed", cause);

		return new UserFeignClient() {

			@Override
			public UserResponse createUser(UserRequest request) {
				throw new DownstreamServiceException("User service is temporarily unavailable", cause);
			}

			@Override
			public UserResponse getUserById(Long id) {
				throw new DownstreamServiceException("User service is temporarily unavailable", cause);
			}
		};
	}
}