package com.raj.trade.ohlc.websocket;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.raj.trade.ohlc.data.OHLCThreadCommData;
import com.raj.trade.ohlc.model.OHLCSubscription;
import com.raj.trade.ohlc.utils.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OHLCWebSocketHandler extends AbstractWebSocketHandler {
	
	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String subscriptionMessage = message.getPayload().replace("\\", "").replace("}\"", "}").replace("\"{", "{");
        log.info("Subscription message - {}",subscriptionMessage);
        try {
			OHLCThreadCommData.getSubscriptionQueue().put(JsonParser.fromJson(subscriptionMessage, OHLCSubscription.class));
			log.debug("Messaged added to subscription list");
		} catch (Exception ex) {
			log.error("Error while sending message to subscription queue",ex);
		}
    }
}
