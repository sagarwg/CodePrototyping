package com.mindcraft.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindcraft.entity.StockListEntity;
import com.mindcraft.entity.UserStockListEntity;
import com.mindcraft.repository.StockRepository;
import com.mindcraft.repository.UserStockListRepository;
import com.mindcraft.utility.ApiResponse;

@RestController
@RequestMapping("/api/stockexchange")
@CrossOrigin(origins = "*")
public class StockControllerMain {
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(StockControllerMain.class);
	
	@Autowired
	private StockRepository stockRepository;

	// Testing purpose
	@GetMapping("/stockexchange/test")
	public ResponseEntity<ApiResponse<String>> getTest() {
		String msg = "Stock Exchange Controller Test, Ok!";
		return ResponseEntity.ok(new ApiResponse<>("Test Passed", msg, true, null, HttpStatus.OK.value()));
	}

	// Add stock values,
	@PostMapping
	public ResponseEntity<ApiResponse<List<StockListEntity>>> addStocks(@RequestBody List<StockListEntity> entity) {
		try {
			List<StockListEntity> saved = stockRepository.saveAll(entity);
			ApiResponse<List<StockListEntity>> response = new ApiResponse<>("Data added successfully", saved, true,
					null, HttpStatus.CREATED.value());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception ex) {
			ApiResponse<List<StockListEntity>> response = new ApiResponse<>("Data not added", null, false,
					ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// READ ALL
	@GetMapping("/allStockData")
	public ResponseEntity<ApiResponse<List<StockListEntity>>> getAllStocks() {
		try {
			List<StockListEntity> list = stockRepository.findAll();
			logger.info("/allStockData - list : " + list);
			return ResponseEntity.ok(new ApiResponse<>("All data retrieved, Count - " + list.size() + "", list, true,
					null, HttpStatus.OK.value()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}


	// READ BY ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<StockListEntity>> getStockById(@PathVariable int id) {
		try {
			Optional<StockListEntity> opt = stockRepository.findById(id);
			if (opt.isPresent()) {
				return ResponseEntity
						.ok(new ApiResponse<>("Data retrieved", opt.get(), true, null, HttpStatus.OK.value()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Data not found", null, false, null, HttpStatus.NOT_FOUND.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Data not retrieved",
					null, false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	// ---------- Not Used ----------
	// UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<StockListEntity>> updateStock(@PathVariable int id,
			@RequestBody StockListEntity entity) {
		try {
			Optional<StockListEntity> opt = stockRepository.findById(id);
			if (opt.isPresent()) {
				StockListEntity existing = opt.get();
				BeanUtils.copyProperties(entity, existing, "id");
				stockRepository.save(existing);
				return ResponseEntity
						.ok(new ApiResponse<>("Data updated", existing, true, null, HttpStatus.OK.value()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Data not found", null, false, null, HttpStatus.NOT_FOUND.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Update failed", null,
					false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteStock(@PathVariable int id) {
		try {
			Optional<StockListEntity> opt = stockRepository.findById(id);
			if (opt.isPresent()) {
				stockRepository.delete(opt.get());
				return ResponseEntity.ok(new ApiResponse<>("Data deleted", null, true, null, HttpStatus.OK.value()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("Data not found", null, false, null, HttpStatus.NOT_FOUND.value()));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Delete failed", null,
					false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}
}
