package com.mindcraft.utility;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindcraft.controller.StockControllerMain;
import com.mindcraft.entity.StockListEntity;
import com.mindcraft.repository.StockRepository;

public class TradeSimulatingFunc {

	@Autowired
	private StockRepository stockRepository;

	public TradeSimulatingFunc(StockRepository stockRepository) {
		super();
		this.stockRepository = stockRepository;
	}

	org.slf4j.Logger logger = LoggerFactory.getLogger(StockControllerMain.class);

	public void updateRandomStockRecord() {
		Double newPrice = null;
		
        // Fetch all stocks from the repository
        List<StockListEntity> allStocks = stockRepository.findAll();
        
        if (allStocks.isEmpty()) {
            System.out.println("No stock records available to update.");
            return;
        }

        // Choose a random stock record from the list
        Random random = new Random();
        StockListEntity randomStock = allStocks.get(random.nextInt(allStocks.size()));
        
        // Update the current price of the selected stock
        randomStock.setTradeInPrice(simulatePrice(randomStock.getCurrentPrice()));
        
        // Save the updated stock back to the database
        stockRepository.save(randomStock);

       logger.info("Value is updated");
}

	public Double simulatePrice(Double currentPrice) {

		// Generate a random value between -5 and 10
		Double randomValue = (double) (Math.random() * 15 - 5); // Random value between -5 and 10

		// Randomly decide whether to add or subtract the random value from the current
		// price
		Double priceChange = Math.random() < 0.5 ? -randomValue : randomValue;

		// Apply the price change to simulate the price going up or down
		Double updatedPrice = currentPrice + priceChange;

		Double roundedValue = Math.round(updatedPrice * 100.0) / 100.0;
		return roundedValue;
	}

	public Double calculatePercentage(Double currentPrice, Double tradeInPrice) {
		// Calculate the percentage change
		double percentageChange = ((tradeInPrice - currentPrice) / currentPrice) * 100;

		// Format the result to two decimal places using DecimalFormat
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(percentageChange));
	}
}
