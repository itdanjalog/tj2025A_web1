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
public class AlarmSocketHandler extends TextWebSocketHandler {

    // 접속한 클라이언트(WebSocketSession)들을 저장할 리스트
    private static final List<WebSocketSession> connList = new Vector<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connList.add(session); // 연결된 세션을 리스트에 추가
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // 접속된 모든 클라이언트에게 메시지 전송 (브로드캐스팅)
        for (WebSocketSession client : connList) {
            try {
                client.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                System.out.println("알람 메시지 전송 오류: " + e.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connList.remove(session); // 연결이 끊긴 세션을 리스트에서 제거
    }
}