package io.cosmos.transfer.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransferWebSockets {
    //
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public void add(WebSocketSession webSocketSession) {
        //
        this.sessions.add(webSocketSession);
    }

    public Optional<WebSocketSession> find(String transferId) {
        //
        return this.sessions.stream().filter(session -> {
            return transferId.equals(session.getAttributes().get("transferId").toString());
        }).findFirst();
    }

    public void remove(WebSocketSession webSocketSession) {
        //
        this.sessions.remove(webSocketSession);
    }
}
