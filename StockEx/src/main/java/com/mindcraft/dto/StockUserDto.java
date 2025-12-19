package com.mindcraft.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUserDto {
	public String ticker;
	public Double tradeInPrice;
	public Integer noOfShares;
	public Integer requestShares;
	@JsonIgnore
	List<StockUserDto> stockControllerUserDto;
}
