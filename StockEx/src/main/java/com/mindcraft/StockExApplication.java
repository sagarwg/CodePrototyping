package com.mindcraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mindcraft.handler.WebSocketHandler;
import com.mindcraft.utility.TradeSimulatingFunc;

@SpringBootApplication
public class StockExApplication implements CommandLineRunner {

	@Autowired
	private WebSocketHandler webSocketHandler;

	public static void main(String[] args) {
		SpringApplication.run(StockExApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//startBroadCasting();
	}
	
	private void startBroadCasting() {
	    new Thread(() -> {
	        try {
	            while (true) {
	                try {
	                    webSocketHandler.broadCastMessageAllStocks();
	                } catch (Exception e) {
	                    // Log error, continue the loop, or take other action
	                    System.err.println("Error broadcasting all stocks: " + e.getMessage());
	                }
	                
	                try {
	                    webSocketHandler.broadCastMessageAllUserStocks();
	                } catch (Exception e) {
	                    // Log error, continue the loop, or take other action
	                    System.err.println("Error broadcasting user stocks: " + e.getMessage());
	                }
	                
//	                try {
//	                    webSocketHandler.broadCastMessageSimulation();
//	                } catch (Exception e) {
//	                    // Log error, continue the loop, or take other action
//	                    System.err.println("Error Simulating data in db: " + e.getMessage());
//	                }

	                Thread.sleep(10000);
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }).start();
	}


//	private void startBroadCasting() {
//		new Thread(() -> {
//			try {
//				while (true) {
//					webSocketHandler.broadCastMessageAllStocks();
//					webSocketHandler.broadCastMessageAllUserStocks();
//					Thread.sleep(3000);
//				}
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//		}).start();
//	}
}
