package com.example.websocket.websockets;

import java.util.Map;

import javax.naming.ConfigurationException;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class CheckUserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap( message );

        if ( SimpMessageType.SUBSCRIBE.equals( accessor.getMessageType() ) ) {
            System.out.println("The destination is: " + accessor.getDestination());

            SecurityContext context = getSecurityContext( accessor );
            Authentication authentication = context.getAuthentication();

            if ( authentication != null ) {
                System.out.println("Requested by user: " + authentication.getName());
            } else {
                System.out.println("Authentication is NULL :(");
            }
        }

        return message;
    }

    private static SecurityContext getSecurityContext(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            throw new WebsocketConfigurationException("You need to pass the HTTP session to the Websocket session");
        }

        return (SecurityContext) sessionAttributes.get("SPRING_SECURITY_CONTEXT");
    }

    public static class WebsocketConfigurationException extends RuntimeException {
        public WebsocketConfigurationException() {
        }

        public WebsocketConfigurationException(String message) {
            super( message );
        }

        public WebsocketConfigurationException(String message, Throwable cause) {
            super( message, cause );
        }
    }
}
