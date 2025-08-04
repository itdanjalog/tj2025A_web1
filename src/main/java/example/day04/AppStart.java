package example.day04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 실행 어노테이션
public class AppStart {
    // AppStart 클래스는 항상 최상위에 위치 한다.
    // -> @SpringBootApplication 은 *현재패키지* 및 *하위패키지*를 스캔 하기 때문에
    public static void main(String[] args) {
        SpringApplication.run( AppStart.class );
    } // main end
} // class end
