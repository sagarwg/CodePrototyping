package com.mindcraft.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBeanRootData {
	@JsonProperty("year")
	public int year;
	@JsonProperty("price")
	public double price;
	@JsonProperty("CPU model")
	public String cPU_model;
	@JsonProperty("Hard disk size")
	public String hard_disk_size;
	
}
