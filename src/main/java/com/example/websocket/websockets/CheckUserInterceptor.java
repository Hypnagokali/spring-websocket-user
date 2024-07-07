package com.example.websocket.websockets;

import com.example.websocket.DummyUser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public class CheckUserInterceptor extends AuthenticatedChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap( message );

        if ( SimpMessageType.SUBSCRIBE.equals( accessor.getMessageType() ) ) {
            System.out.println("The destination is: " + accessor.getDestination());
            DummyUser authenticatedUser = getAuthenticatedUser( message );
            System.out.println("Authenticated user: "  + authenticatedUser.getEmail());
        }

        return message;
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
