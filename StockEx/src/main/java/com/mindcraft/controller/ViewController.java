package com.mindcraft.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mindcraft.dto.UserDto;

import tools.jackson.databind.ser.jdk.UUIDSerializer;

@Controller
public class ViewController {
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

	UserDto userDto;
	String msg = null;

	@GetMapping("/")
	public String index() {
		return "Index";
	}

	@GetMapping("/home")
	public String home() {
			return "AllStocks";
	}

	@GetMapping("/AllStocksUser")
	public String user() {
			return "AllStocksUser";
	}

	@GetMapping("/logout")
	public String logout() {
		//userDto.setLoggedInUser("msg");
		//logger.info("msg : " + userDto.getLoggedInUser());
		return "Logout";
	}

	@GetMapping("/LoggedinUser")
	public String loggedInUser() {
		msg = userDto.getLoggedInUser();
		if (msg == null) {
			msg = "Kindly relogin";
			return "Index";
		} else {
			return msg;
		}
	}
	
	@GetMapping("/StockPrice")
	public String getStockPrice() {
			return "WebStock";
	}
	
	@GetMapping("/WebStocksLive")
	public String getWebStocksLive() {
			return "WebStocksLive";
	}
}