package io.cosmos.transfer.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TransferWebSocketInterceptor extends HttpSessionHandshakeInterceptor {
    //
    @Override
    public boolean beforeHandshake(ServerHttpRequest httpRequest, ServerHttpResponse httpResponse, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest request = (ServletServerHttpRequest) httpRequest;
        HttpServletRequest servletRequest = request.getServletRequest();
        attributes.put("transferId", servletRequest.getParameter("transferId"));

        return super.beforeHandshake(httpRequest, httpResponse, wsHandler, attributes);
    }
}
