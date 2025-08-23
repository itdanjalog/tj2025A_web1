package web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import web.controller.AlarmSocketHandler;
import web.controller.ChatSocketHandler;

@Configuration
@EnableWebSocket // 스프링부트의 웹소켓 기능 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatSocketHandler chatSocketHandler; // 채팅 핸들러 주입

    @Autowired
    private AlarmSocketHandler alarmSocketHandler; // 알람 핸들러 주입

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 1. 채팅 소켓 핸들러 등록
        registry.addHandler(chatSocketHandler, "/chatsocket")
                .setAllowedOrigins("*"); // CORS 허용

        // 2. 알람 소켓 핸들러 등록
        registry.addHandler(alarmSocketHandler, "/alarmsocket")
                .setAllowedOrigins("*"); // CORS 허용
    }
}