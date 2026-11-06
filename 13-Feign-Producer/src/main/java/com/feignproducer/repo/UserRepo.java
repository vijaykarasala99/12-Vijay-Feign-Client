package com.feignproducer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feignproducer.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

}