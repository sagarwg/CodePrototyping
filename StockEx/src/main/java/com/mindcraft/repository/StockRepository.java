package com.mindcraft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindcraft.entity.StockListEntity;

public interface StockRepository extends JpaRepository<StockListEntity, Integer>{
	 Optional<StockListEntity> findByTickerSymbol(String tickerSymbol);
	 
	 @Query("SELECT s.tickerSymbol FROM StockListEntity s")
	 List<String> findAllTickers();
}
