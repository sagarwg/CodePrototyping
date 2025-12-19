package com.mindcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindcraft.entity.UserStockListEntity;

public interface UserStockListRepository extends JpaRepository<UserStockListEntity, Integer>{
	Optional<UserStockListEntity> findByTickerSymbol(String tickerSymbol);
}
