package com.vijaystack.feign.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijaystack.feign.dto.UserRequest;
import com.vijaystack.feign.dto.UserResponse;
import com.vijaystack.feign.service.UserConsumerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/consumer/api/users")
@Validated
public class UserConsumerController {

	private final UserConsumerService userService;

	public UserConsumerController(UserConsumerService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
		UserResponse response = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable @Positive Long id) {
		UserResponse response = userService.getUserById(id);
		return ResponseEntity.ok(response);
	}
}