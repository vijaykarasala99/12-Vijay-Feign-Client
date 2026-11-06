package com.vijaystack.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vijaystack.feign.config.FeignClientConfiguration;
import com.vijaystack.feign.dto.UserRequest;
import com.vijaystack.feign.dto.UserResponse;
import com.vijaystack.feign.fallback.UserFeignClientFallbackFactory;

@FeignClient(name = "userService", url = "${integration.user-service.base-url}", configuration = FeignClientConfiguration.class, fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

	@PostMapping("/postuser")
	UserResponse createUser(@RequestBody UserRequest request);

	@GetMapping("/getuser/{id}")
	UserResponse getUserById(@PathVariable("id") Long id);
}