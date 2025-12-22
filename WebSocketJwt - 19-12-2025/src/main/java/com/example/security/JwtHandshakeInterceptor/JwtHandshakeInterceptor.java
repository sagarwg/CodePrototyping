package com.example.security.JwtHandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.securityJwtUtil.JwtUtil;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();

        // 1) Read JWT from query param
        String token = servletRequest.getParameter("token");

        // 2) Try Authorization header if query is null
        if (token == null) {
            String auth = servletRequest.getHeader("Authorization");
            if (auth != null && auth.startsWith("Bearer ")) {
                token = auth.substring(7);
            }
        }

        // Token missing?
        if (token == null) {
            System.out.println("Token missing");
            return false;   // BLOCK connection
        }

        // Token invalid?
        if (!jwtUtil.validateToken(token)) {
            System.out.println("Invalid token: " + token);
            return false;   // BLOCK connection
        }

        // Token valid → extract user
        String username = jwtUtil.extractUsername(token);
        attributes.put("username", username);

        System.out.println("Token validated. User = " + username);

        return true; // allow WebSocket handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               @Nullable Exception exception) {

        // No action needed
    }
}