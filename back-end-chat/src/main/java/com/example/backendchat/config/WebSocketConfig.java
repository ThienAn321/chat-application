package com.example.backendchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket message broker.
 * Enables and configures the WebSocket connection and message broker for handling STOMP messaging.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker for handling messaging routes and destinations.
     *
     * @param config the MessageBrokerRegistry used to configure message broker options
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/user", "/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers the WebSocket endpoints that clients will use to connect to the WebSocket server.
     *
     * @param registry the StompEndpointRegistry used to register STOMP endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
