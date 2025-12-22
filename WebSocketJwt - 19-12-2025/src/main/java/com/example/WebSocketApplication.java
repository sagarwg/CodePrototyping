package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.handler.WebSocketHandler;
import com.example.securityJwtUtil.JwtUtil;

@SpringBootApplication
@EnableScheduling
public class WebSocketApplication implements CommandLineRunner {

	@Autowired
	private WebSocketHandler webSocketHandler;

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
		JwtUtil util = new JwtUtil();
		String token = util.generateToken("sagar");
		System.out.println("Generated Token: " + token);
	}

	public void run(String... args) throws Exception {
		startBroadCasting();
	}

	private void startBroadCasting() {
		/*
		 * new Thread(() -> { try { while (true) { webSocketHandler.broadMessage();
		 * Thread.sleep(1000); } } catch (InterruptedException e) {
		 * Thread.currentThread().interrupt(); } }).start();
		 */
		webSocketHandler.broadMessage();
	}
}
