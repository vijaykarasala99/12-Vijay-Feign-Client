package com.vijaystack.feign.service;

import org.springframework.stereotype.Service;

import com.vijaystack.feign.client.UserFeignClient;
import com.vijaystack.feign.dto.UserRequest;
import com.vijaystack.feign.dto.UserResponse;

@Service
public class UserConsumerService {

	private final UserFeignClient userFeignClient;

	public UserConsumerService(UserFeignClient userFeignClient) {
		this.userFeignClient = userFeignClient;
	}

	public UserResponse createUser(UserRequest request) {
		return userFeignClient.createUser(request);
	}

	public UserResponse getUserById(Long id) {
		return userFeignClient.getUserById(id);
	}
}