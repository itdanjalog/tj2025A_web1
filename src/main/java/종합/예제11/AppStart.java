package 종합.예제11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
    public static void main(String[] args) {
        // 직접적으로 view 실행하는게 아닌 스프링( 부트포함 + @SpringBootApplication ) 실행
        SpringApplication.run( AppStart.class );
    }
}
