package com.example.handler;

import java.util.ArrayList;
import java.util.List;

import com.example.WebSocketApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.springframework.web.socket.CloseStatus;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	// private final WebSocketApplication webSocketApplication;

	private List<WebSocketSession> sessions = new ArrayList<>();
	private int counter = 1;

	/*
	 * WebSocketHandler(WebSocketApplication webSocketApplication) {
	 * this.webSocketApplication = webSocketApplication; }
	 */

	/*
	 * @Override public void afterConnectionEstablished(WebSocketSession session)
	 * throws Exception { sessions.add(session);
	 * System.out.println("WebSocket connection Establised" + session.getId()); }
	 */

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String username = (String) session.getAttributes().get("username");
		System.out.println("Connected user: " + username);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		System.out.println("Msg send by client: " + message.getPayload());
		if (message.toString().contains("Hi")) {
			session.sendMessage(new TextMessage("Hi, Welcome to Mindcraft...!!!!"));
		} else {
			session.sendMessage(new TextMessage("Error"));
		}

		String username = (String) session.getAttributes().get("username");
		System.out.println("Message from " + username + ": " + message.getPayload());

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		System.out.println("WebSocket connection Closed" + session.getId());
	}

	public void broadMessage() {
		String message = "";// "Hi from Sagar" + counter++;

		for (WebSocketSession session : sessions)
			try {
				session.sendMessage(new TextMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}
