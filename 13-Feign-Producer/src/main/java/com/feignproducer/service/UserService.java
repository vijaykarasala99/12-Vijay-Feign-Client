package com.feignproducer.service;

import org.springframework.stereotype.Service;

import com.feignproducer.entity.UserEntity;
import com.feignproducer.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo userRepository;

	public UserService(UserRepo userRepository) {
		this.userRepository = userRepository;
	}

	public UserEntity saveUser(UserEntity user) {
		return userRepository.save(user);
	}

	public UserEntity getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
	}
}