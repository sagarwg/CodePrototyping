package com.example.webSocketConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.handler.WebSocketHandler;
import com.example.security.JwtHandshakeInterceptor.JwtHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private WebSocketHandler webSocketHandler;

	public WebSocketConfig(WebSocketHandler webSocketHandler) {
		this.webSocketHandler = webSocketHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/message").addInterceptors(new JwtHandshakeInterceptor()) // <-- Here
				.setAllowedOrigins("*");
	}

	/*
	 * @Override // @CrossOrigin("*") public void
	 * registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	 * registry.addHandler(webSocketHandler, "/message").setAllowedOrigins("*"); }
	 */
}
