package io.cosmos.transfer.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TransferWebSocketHandler extends TextWebSocketHandler {
    //
    private final TransferWebSockets transferWebSockets;

    public TransferWebSocketHandler(TransferWebSockets transferWebSockets) {
        this.transferWebSockets = transferWebSockets;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //
        System.out.println("afterConnectionEstablished - " + session.getAttributes().get("transferId"));
        transferWebSockets.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //
        String transferId = message.getPayload();
        System.out.println("handleTextMessage - " + transferId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //
        System.out.println("afterConnectionClosed");
        transferWebSockets.remove(session);
    }
}
