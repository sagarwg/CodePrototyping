package com.mindcraft.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindcraft.dto.StockMainWsDto;
import com.mindcraft.dto.StockUserWsDto;
import com.mindcraft.entity.StockListEntity;
import com.mindcraft.entity.UserStockListEntity;
import com.mindcraft.repository.StockRepository;
import com.mindcraft.repository.UserStockListRepository;
import com.mindcraft.utility.TradeSimulatingFunc;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private UserStockListRepository userStockListRepository;
	
	@Autowired
	private StockRepository stockRepository;

	private List<WebSocketSession> sessions = new ArrayList<>();

	private int counter = 1;
	
	StockMainWsDto dtoWsAs;
	StockUserWsDto dtoUs;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		System.out.println("WebSocket Connection Established" + session.getId());
	}

	// To handle incoming message
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message);
	}
	
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		System.out.println(message);
//		String retMsg = null;
//		String opt1 = "A";
//		if(message.getPayload().equals(opt1)) {
//			retMsg = "You have chosen option 'A'";
//			session.sendMessage(new TextMessage(retMsg));
//		} else {
//				retMsg = "Only option 'A' is available at this moment";
//				session.sendMessage(new TextMessage(retMsg));}
//	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
			throws Exception {
		sessions.remove(session);
		System.out.println("WebSocket Connection Closed:" + session.getId());
	}

	// endpoint sample - ws://localhost:8080/message
	public void broadCastMessageAllStocks() {
		String message = "Hi From Sagar " + counter++;

		double changedPrice;
		
		List<StockListEntity> getAllStocks = stockRepository.findAll();
//		List<StockMainWsDto> getAllStocksDto = new ArrayList<>();
//		for (StockListEntity entity : getAllStocks) {
//			dtoWsAs = new StockMainWsDto();
//			BeanUtils.copyProperties(entity, dtoWsAs);
//	        //changedPrice = TradeSimulatingFunc.simulatePrice(dtoWsAs.getCurrentPrice());
//	        //dtoWsAs.setTradeInPrice(changedPrice);
//	        //dtoUs.setTradeInPrice(changedPrice);
//	        getAllStocksDto.add(dtoWsAs);
//	    }
		
		
		Map<String, Object> messageData = new HashMap<>();
		messageData.put("type", "all-stocks"); // Add the identifier for the message type
		messageData.put("data", getAllStocks);
		
		for (WebSocketSession session : sessions)
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				//String jsonMessage = objectMapper.writeValueAsString(getAllStocksDto);
				//session.sendMessage(new TextMessage(dto.getTradeInPrice().toString()));
				String jsonMessage = objectMapper.writeValueAsString(messageData); 
				session.sendMessage(new TextMessage(jsonMessage));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void broadCastMessageAllUserStocks() {
		String message = "Hi From Sagar " + counter++;
		
		double difference;
		TradeSimulatingFunc tradeSimulatingFunc = new TradeSimulatingFunc(stockRepository);
		tradeSimulatingFunc.updateRandomStockRecord();
		List<UserStockListEntity> getAllUserStocks = userStockListRepository.findAll();
//		List<StockUserWsDto> getAllUserStocksDto = new ArrayList<>();
//		for (UserStockListEntity entity : getAllUserStocks) {
//			dtoUs = new StockUserWsDto();
//			BeanUtils.copyProperties(entity, dtoUs);
//			difference = TradeSimulatingFunc.calculatePercentage(dtoUs.getCurrentPrice(), dtoUs.getTradeInPrice());
//			dtoUs.setDifference(difference);
//			dtoUs.setTradeInPrice(dtoWsAs.getTradeInPrice());
//			getAllUserStocksDto.add(dtoUs);
//			
//		}
//		
		Map<String, Object> messageData = new HashMap<>();
		messageData.put("type", "all-user-stocks"); // Add the identifier for the message type
		messageData.put("data", getAllUserStocks);
		for (WebSocketSession session : sessions)
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				//String jsonMessage = objectMapper.writeValueAsString(getAllUserStocks);
				//session.sendMessage(new TextMessage(dto.getTradeInPrice().toString()));
				String jsonMessage = objectMapper.writeValueAsString(messageData); 
				session.sendMessage(new TextMessage(jsonMessage));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
//	public void broadCastMessageSimulation() {
//		TradeSimulatingFunc.updateRandomStockRecord();
//	}
	
	// endpoint sample - ws://localhost:8080/message
//		public void broadCastMessage() {
//			//String message = "Hi From Sagar " + counter++;
//			String message = "Hi Press A for chatting " + counter++;
//			for (WebSocketSession session : sessions)
//				try {
//					session.sendMessage(new TextMessage(message));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//		}
}


