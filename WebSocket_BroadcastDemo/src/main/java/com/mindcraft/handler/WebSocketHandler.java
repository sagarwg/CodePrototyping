package com.mindcraft.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new ArrayList<>();

	private int counter = 1;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		System.out.println("WebSocket Connection Established" + session.getId());
	}

//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		System.out.println(message);
//	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message);
		String retMsg = null;
		String opt1 = "A";
		if(message.getPayload().equals(opt1)) {
			retMsg = "You have chosen option 'A'";
			session.sendMessage(new TextMessage(retMsg));
		} else {
				retMsg = "Only option 'A' is available at this moment";
				session.sendMessage(new TextMessage(retMsg));}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
			throws Exception {
		sessions.remove(session);
		System.out.println("WebSocket Connection Closed:" + session.getId());
	}

	// endpoint sample - ws://localhost:8080/message
//	public void broadCastMessage() {
//		String message = "Hi From Sagar " + counter++;
//		for (WebSocketSession session : sessions)
//			try {
//				session.sendMessage(new TextMessage(message));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
	
	// endpoint sample - ws://localhost:8080/message
		public void broadCastMessage() {
			//String message = "Hi From Sagar " + counter++;
			String message = "Hi Press A for chatting " + counter++;
			for (WebSocketSession session : sessions)
				try {
					session.sendMessage(new TextMessage(message));
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
}


