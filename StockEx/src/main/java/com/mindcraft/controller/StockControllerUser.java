package com.mindcraft.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindcraft.dto.StockUserDto;
import com.mindcraft.entity.StockListEntity;
import com.mindcraft.entity.UserStockListEntity;
import com.mindcraft.repository.StockRepository;
import com.mindcraft.repository.UserStockListRepository;
import com.mindcraft.utility.ApiResponse;

@RestController
@RequestMapping("/api/stockuser")
@CrossOrigin(origins = "*")
public class StockControllerUser {
	org.slf4j.Logger logger = LoggerFactory.getLogger(StockControllerUser.class);
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private UserStockListRepository userStockListRepository;

	// Testing purpose
	@GetMapping("/stockuser/test")
	public ResponseEntity<ApiResponse<String>> getTest() {
		String msg = "Stock User Controller Test, Ok!";
		return ResponseEntity.ok(new ApiResponse<>("Test Passed", msg, true, null, HttpStatus.OK.value()));
	}

	@GetMapping("/allUserStockData")
	public ResponseEntity<ApiResponse<List<UserStockListEntity>>> allUserStockData() {
		try {
			List<UserStockListEntity> list = userStockListRepository.findAll();
			logger.info("list : " + list);
			return ResponseEntity.ok(new ApiResponse<>("All data retrieved, Count - " + list.size() + "", list, true,
					null, HttpStatus.OK.value()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	// to update/insert stocks into user table // Buy
	@PostMapping("/stocksTradeApiu")
	public ResponseEntity<ApiResponse<UserStockListEntity>> stocksTradeApiu(@RequestBody StockUserDto ticker) {
		logger.info("Ticker :" + ticker.getTicker());
		logger.info("TradeInPrice :" + ticker.getTradeInPrice());
		logger.info("RequestShares :" + ticker.getRequestShares());
		
		// logger.info("action :"+action);// buy
		try {
			Optional<UserStockListEntity> optCheck = userStockListRepository.findByTickerSymbol(ticker.getTicker());
			Optional<StockListEntity> opt = stockRepository.findByTickerSymbol(ticker.getTicker());
			if (optCheck.isPresent() && opt.get().getNoOfShares() < ticker.getRequestShares()) {

				return ResponseEntity.status(HttpStatus.CONFLICT).body(
						new ApiResponse<>("Stock's already purchased!", null, false, null, HttpStatus.CONFLICT.value()));
			} else {
				try {				
					if (opt.isPresent()) {
						opt.get().setTradeInPrice(ticker.getTradeInPrice());
						UserStockListEntity existing = new UserStockListEntity();
						
						int mainTbShares = opt.get().getNoOfShares();
						int requestedShares = ticker.getRequestShares();
						opt.get().setNoOfShares(mainTbShares - requestedShares);
						int userTbShares = existing.getNoOfShares();				
						
						BeanUtils.copyProperties(opt.get(), existing, "id", "noOfShares");
						existing.setNoOfShares(userTbShares+requestedShares);
						userStockListRepository.save(existing);
						return ResponseEntity
								.ok(new ApiResponse<>("Stock purchased.", existing, true, null, HttpStatus.OK.value()));
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
								new ApiResponse<>("Data not found", null, false, null, HttpStatus.NOT_FOUND.value()));
					}
				} catch (Exception ex) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
							"Update failed", null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
				}
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Update failed", null,
					false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	// to delete stocks from user table
	@DeleteMapping("/stocksTradeApid")
	public ResponseEntity<ApiResponse<Void>> stocksTradeApid(@RequestBody StockUserDto ticker) {
		logger.info("ticker.getTicker() :"+ticker.getTicker());
		try {
			Optional<UserStockListEntity> opt = userStockListRepository.findByTickerSymbol(ticker.getTicker()
					);
			if (opt.isPresent()) {
				userStockListRepository.delete(opt.get());
				return ResponseEntity.ok(new ApiResponse<>("Stock sold.", null, true, null, HttpStatus.OK.value()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Stock not found", null, false, null, HttpStatus.NOT_FOUND.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Delete failed", null,
					false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	@GetMapping("/allUserSubStocks")
	public ResponseEntity<ApiResponse<List<StockListEntity>>> allUserSubStocks() {
		try {
			List<StockListEntity> list = stockRepository.findAll();
			logger.info("list : " + list);
			return ResponseEntity.ok(new ApiResponse<>("All data retrieved", list, true, null, HttpStatus.OK.value()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}
	
	
	
	@GetMapping("/updateRandomStockRecord")
	public void updateRandomStockRecord() {
		Double newPrice = null;
		
        // Fetch all stocks from the repository
        List<StockListEntity> allStocks = stockRepository.findAll();
        logger.info("allStocks: "+allStocks);
        if (allStocks.isEmpty()) {
            System.out.println("No stock records available to update.");
            return;
        }

        // Choose a random stock record from the list
        Random random = new Random();
        StockListEntity randomStock = allStocks.get(random.nextInt(allStocks.size()));
        logger.info("randomStock: "+randomStock);
        // Update the current price of the selected stock
        randomStock.setCurrentPrice(simulatePrice(randomStock.getCurrentPrice()));
        
        // Save the updated stock back to the database
        //stockRepository.save(randomStock);
}

	public static Double simulatePrice(Double currentPrice) {

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
}
