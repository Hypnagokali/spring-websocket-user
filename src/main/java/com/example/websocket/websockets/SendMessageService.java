package com.example.websocket.websockets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class SendMessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 2000)
    public void doSendMessage() {
        simpMessagingTemplate.convertAndSend( "/topic/test", new SimpleMessage( "Test content" ) );
    }

    @RequiredArgsConstructor
    @Getter
    public class SimpleMessage {
        private final String content;

    }

}
