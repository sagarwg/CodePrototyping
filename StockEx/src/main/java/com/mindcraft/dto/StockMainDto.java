package com.mindcraft.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMainDto {
	public String ticker;
	@JsonIgnore
	List<StockMainDto> stockControllerUserDto;
}
