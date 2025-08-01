package example.day03;

// AppStart : 스프링 실행과 동시에 같은패키지/하위패키지내 모든 컴포넌트 스캔 자동 빈(객체)등록
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// @Controller // 스프링 에게 해당 클래스는 Controller 입니다.알림.
// 어노테이션이란? (라벨) 택배상자안에 있는 실질적인 내용물이 아닌, 택배상자 앞에 붙인 '유리 취급주의' 부착
// + HTTP(웹통신) 지원 + @Component 포함( @SpringBootApplication 가 스캔하는 대상 )
@RestController // @Controller( +Component ) + @ResponseBody
public class RestController1 {

    // 싱글톤 생략 : @Component 사용했기 때문에

    @GetMapping("/day03") // HTTP(웹 통신 규약) 중에서 'GET' METHOD 사용 , baseUrl 뒤로 자원URL 정의
    // url : http://localhost:8080/day03
    // Whitelabel Error Page : 요청은 정상이지만 , 응답은 없다.
    // * Talend API : 브라우저(주소입력창) 에서 HTTP 웹주소 입력은 GET 방식만 지원한다.
    // @ResponseBody // HTTP 요청에 따른 처리후 응답을 자바 타입에서 HTTP 타입으로 *자동변환* , 주로 JSON(자바스크립트객체)
    // --> 현재 Controller 가 @RestController 이면 생략 가능.
    public String method1(){
        return "자바에서 보내온 메시지";
    } // class end

    // +++++++++++ 매개변수 +++++++++++
    // 메소드에서 매개변수란? 메소드 호출시 메소드 안으로 들어오는 값을 저장하는 변수
    // + HTTP 요청시에도 인자값/자원 보낼수 있다.
    // ++ 자바 메소드 인자값 전달 방식 : method2( 유재석 );
    // ++ HTTP 메소드 인자값 전달 방식 : http://localhost:8080/day03/param1?name=유재석
    // +++ 쿼리스트링 이란? URL(주소) 뒤에 ? 붙이고 자원(데이터) 이동/전달/표현 할때 사용하는 문법
    // ++++ URL?매개변수명1=값1&매개변수명2=값2&매개변수명3=값3   ( 주의할점 : 문자만 가능 )
    @GetMapping("/day03/param1")
    public boolean method2( @RequestParam( name = "name" , defaultValue = "홍길동" , required = false ) String name ){
        // @RequestParam : Request(요청)+Param(매개변수) 요청매개변수 매핑 어노테이션
        // + @RequestParam( name = "URL매개변수명" , defaultValue = "초기값" , required = true/false );
        // ++ name = "URL매개변수명" : 주소(URL) 상의 ? 뒤로 매핑(연결)할 매개변수명 명시 , 단 자바 매개변수와 이름이 동일시 생략가능
            // 예] http://localhost:8080/day03/param1?name=유재석 ---> String name
            // 예] http://localhost:8080/day03/param1?name=유재석 ---> @RequestParam( name = "name") String nickName
        // ++ defaultValue = "홍길동" : 만약에 매개변수명에 값이 생략되면 자동으로 들어가는 값
            // 예] http://localhost:8080/day03/param1?name=유재석 ---> "유재석"
            // 예] http://localhost:8080/day03/param1            ---> "홍길동"
        // ++ required : 만약에 해당 매개변수가 없으면 예외 (자동) 발생 , HTTP 400 ERROR(잘못된 요청 뜻)
            // 예] http://localhost:8080/day03/param1?name=유재석   ---> 정상
            // 예] http://localhost:8080/day03/param1              ---> 400 ERROR
        System.out.println("RestController1.method2"); // soutm 현재 메소드명 출력
        System.out.println("name = " + name); // soutp 현재 메소드 매개변수 출력
        return true;
    } // m end

    @GetMapping("/day03/param2")
    // http://localhost:8080/day03/param2 ? name = 강호동 & age = 20
    public int method3( @RequestParam( required = true ) String name ,
                        @RequestParam( name = "age") int 나이 ){ // *자동타입 변환*
        // + 통신 확인 방법 : soutm , soutp
        System.out.println("RestController1.method3");
        System.out.println("name = " + name + ", age = " + 나이 );
        return 3; // 임의 값 반환
    } // m end

    @DeleteMapping("/day03/param3")
    public String method4( @RequestParam Map<String , String > map ){
            // 일반적으로 정해진 규칙이나 매개변수명이 아닌 단순 MAP(비규칙) 구조일 경우 @RequestParam 사용해야한다.
        System.out.println("RestController1.method4");
        // Map : KEY 와 VALUE 를 한쌍(ENTRY) 구성하여 여러개 ENTRY 저장 구조
        System.out.println("map = " + map);
        return "안녕"; // 임의값
    } // m end

    @DeleteMapping("/day03/param4")
    // http://localhost:8080/day03/param4?name=유재석&age=40
    // ++ DTO 에 자동 매핑 하기 위해서는 DTO 에도 name 과 age 멤버변수가 존재 해야한다.+생성자+setter
    public int method5(  @ModelAttribute TaskDto taskDto ){
        // new TaskDto() 생략해도 자동으로 생성된다.
        System.out.println("RestController1.method5");
        System.out.println("taskDto = " + taskDto);
        return 3;
    }
    /*
    * 1) 쿼리스트링이란 ? URL경로상의 매개변수 표현 , 매개변수 노출O , 보안 위험 ,  URL?매개변수=값&매개변수=값
    * 2) BODY(본문)이란 ? HTTP본문에 매개변수 표현 , 매개변수 노출X , 보안 안전 , POST/PUT 에서 JSON타입으로 객체 지원

                    @RequestParam                           vs        @ModelAttribute                               @RequestBody
    역할            단일 파라미터에 변수 바인딩                           복수 파라미터에 객체 바인딩                         본문(body) 객체 바인딩
    생략기준         변수명이 일치할경우(쿼리스트링의매개변수명)             DTO 타입일때 기본적용(+생성자,+Setter)              DTO
    주요타입         기본형:int/String/List/Map                         DTO(개발자가만든객체)                              DTO ( JSON ) , List/Map
    처리가능요청     쿼리스트링, <form:첨부파일x>                         쿼리스트링,<form:첨부파일o>                        본문(body)
    HTTP메소드      GET/POST/PUT/DELETE                                GET/POST/PUT/DELETE                             POST/PUT

    */

    @PostMapping("/day03/param5")
    // Talend API : [Method] Post , [Scheme] http://localhost:8080/day03/param5
    //              [HEADERS] JSON  [BODY] { "name" : "유재석" , "age" : "40" }
    public boolean method6( @RequestBody TaskDto taskDto ){
        System.out.println("RestController1.method6");
        System.out.println("taskDto = " + taskDto);
        return true;// 임의값
    }

    @PutMapping("/day03/param6")
    public int method7( @RequestBody Map<String,String> map ){
        System.out.println("RestController1.method7");
        System.out.println("map = " + map);
        return 3;
    }


} // class end











