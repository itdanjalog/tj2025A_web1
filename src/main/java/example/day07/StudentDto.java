package example.day07;

import lombok.*;

// 롬복이란 : 설계에서 자주 사용되는 코드를 자동생성
// 롬복 설치
// 1) (환경)인텔리제이 설치 : [파일]->[설정]->[플러그인]->lombok 검색 -> 설치
// 2) (프로젝트)그레이들 설치 :
//      1. https://start.spring.io/
//      2. 디펜더시 에서 'lombok' 검색
//      3.  'build.gradle' 파일내 dependencies{} 추가.
//           compileOnly 'org.projectlombok:lombok'
//           annotationProcessor 'org.projectlombok:lombok'
@NoArgsConstructor // 빈생성자 *(컴파일시/실행) 자동 생성
@AllArgsConstructor // 모든매개변수생성자 *(컴파일시/실행) 자동 생성
@Getter // 모든 멤버변수에 대해서 getter 메소드 *
@Setter // 모든 멤버변수에 대해서 setter 메소드 *
@ToString // 객체 조회시 주소값 대신에 모든 멤버변수를 문자열 출력 메소드
public class StudentDto {
    // 1. 멤버변수
    private int sno;        // 번호
    private String sname;   // 이름
    private int skor;       // 국어점수
    private int smath;      // 수학점수
    private String sdate;   // 등록일
    // 2. 생성자 : 빈생성자,풀(모든매개변수)생성자
    // 3. setter and getter , toString
}
