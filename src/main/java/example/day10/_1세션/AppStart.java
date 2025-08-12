package example.day10._1세션;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart { // 2025-08-12: 부트 앱 시작 클래스(추가 설정 없이 동작)
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}