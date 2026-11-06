package com.feignproducer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.feignproducer.entity.UserEntity;
import com.feignproducer.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/postuser")
	public UserEntity createUser(@RequestBody UserEntity user) {
		return userService.saveUser(user);
	}

	@GetMapping("/getuser/{id}")
	public UserEntity getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}
}