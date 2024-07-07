package com.example.websocket.websockets;

import java.util.Map;

import com.example.websocket.DummyUser;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class AuthenticatedChannelInterceptor implements ChannelInterceptor {

    protected DummyUser getAuthenticatedUser(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap( message );
        SecurityContext context = getSecurityContext( accessor );
        Authentication authentication = context.getAuthentication();

        return new DummyUser( authentication.getName() );
    }


    private static SecurityContext getSecurityContext(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            throw new CheckUserInterceptor.WebsocketConfigurationException("You need to pass the HTTP session to the Websocket session");
        }

        return (SecurityContext) sessionAttributes.get("SPRING_SECURITY_CONTEXT");
    }

}
