package com.cxp.websocketbasic.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author : cheng
 * @date : 2020-04-03 20:07
 */
public class ChatMessageHandler extends TextWebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connect to the websocket success......");
        session.sendMessage(new TextMessage("I am Server!"));
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("From Client message: {}", payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("websocket connection closed......");
        super.afterConnectionClosed(session, status);
    }
}
