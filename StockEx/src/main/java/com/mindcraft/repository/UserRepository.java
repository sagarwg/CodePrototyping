package com.mindcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindcraft.entity.StockListEntity;
import com.mindcraft.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// Custom finder by username
	Optional<UserEntity> findByUsername(String username);
}
