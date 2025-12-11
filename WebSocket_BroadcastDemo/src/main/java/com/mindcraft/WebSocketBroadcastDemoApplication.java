package com.mindcraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mindcraft.handler.WebSocketHandler;

@SpringBootApplication
@EnableScheduling
public class WebSocketBroadcastDemoApplication implements CommandLineRunner{

	@Autowired
	private WebSocketHandler webSocketHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(WebSocketBroadcastDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		startBroadCasting();	
	}
	
//	private void startBroadCasting() {
//		new Thread(()-> {
//			try {
//				while(true) {
//					webSocketHandler.broadCastMessage();
//					Thread.sleep(3000);
//				}
//			} catch(InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//		}).start();
//	}
	
	private void startBroadCasting() {
		new Thread(()-> {
			try {
				while(true) {
					webSocketHandler.broadCastMessage();
					Thread.sleep(3000);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}).start();
	}
}
