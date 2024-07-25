package io.cosmos.configuration;

import io.cosmos.transfer.websocket.TransferWebSocketHandler;
import io.cosmos.transfer.websocket.TransferWebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    //
    private final TransferWebSocketHandler transferWebSocketHandler;

    public WebSocketConfiguration(TransferWebSocketHandler transferWebSocketHandler) {
        //
        this.transferWebSocketHandler = transferWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //
        webSocketHandlerRegistry.addHandler(transferWebSocketHandler, "/transfers")
                .setAllowedOrigins("*")
                .addInterceptors(new TransferWebSocketInterceptor());
    }
}


