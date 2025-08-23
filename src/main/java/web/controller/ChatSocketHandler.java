package web.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    // 접속한 클라이언트(WebSocketSession)들을 저장할 리스트
    private static final List<WebSocketSession> 접속명단 = new Vector<>();

    // 1. 클라이언트가 서버에 접속 성공했을 때 (afterConnectionEstablished)
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("클라이언트 접속 성공: " + session);
        접속명단.add(session);
    }

    // 2. 클라이언트가 서버에게 메시지를 보냈을 때 (handleTextMessage)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 클라이언트가 보낸 메시지
        System.out.println("받은 메시지: " + payload);

        // 접속한 모든 클라이언트에게 받은 메시지를 브로드캐스팅
        for (WebSocketSession clientSocket : 접속명단) {
            try {
                clientSocket.sendMessage(message);
            } catch (IOException e) {
                System.out.println("메시지 전송 오류: " + e.getMessage());
            }
        }
    }

    // 3. 클라이언트가 서버와 접속이 끊겼을 때 (afterConnectionClosed)
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("클라이언트 접속 해제: " + session);
        접속명단.remove(session);
    }
}