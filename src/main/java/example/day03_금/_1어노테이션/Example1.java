package example.day03_금._1어노테이션;


import org.springframework.stereotype.Controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// [1] 어노테이션 만들기
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
@interface Annotation2{
    // (1) interface 타입 앞에 @ 붙인다.
    // (2) 어노테이션 명을 임의로 지정한다.
    // (3) 어노테이션 명 위에 @Retention( RetentionPolicy.RUNTIME )을 붙여
    //    '런타임' 시점까지 어노테이션 정보가 유지되도록 설정한다.
    //    이렇게 해야 리플렉션으로 정보를 조회할 수 있다.
    // (4) 어노테이션 명 위에 @Target( ElementType.적용할대상 ) 적용할 대상 설정한다.
    // (5) 추상 메소드를 만든다.  + default 기본값
    String value1();
    int value2() default 100;

    // 1. 문법적 관점: 추상 메소드 (Abstract Method)
    //@interface는 자바 컴파일러가 내부적으로 java.lang.annotation.Annotation 인터페이스를 상속받도록 처리합니다.
    // 인터페이스 안에는 구현부가 없는 메소드, 즉 추상 메소드만 선언할 수 있습니다.
    //
    //따라서 String value();는 문법적인 형태상 추상 메소드가 맞습니다.
    //
    //2. 기능적 관점: 속성 (Attribute / Element)
    //이 추상 메소드는 실제 로직을 구현하기 위한 것이 아니라, 어노테이션에 데이터를 저장하기 위한 '키(key)' 역할을 합니다.
    // 즉, 어노테이션이 가질 수 있는 **데이터 항목(속성)**을 정의하는 것입니다.
    //
    //Java
    //
    //// @RequestMapping 어노테이션의 'value'라는 속성에 "/login" 값을 저장함
    //@RequestMapping("/login")
    //public void login() {}
    //이처럼 실제 사용 목적이 '값을 정의하고 저장'하는 것이기 때문에, 일반적으로는 속성(Attribute) 또는 **엘리먼트(Element)**라고 부릅니다.
    //
    //결론 ⭐️
    //문법적 형태는 추상 메소드이지만, 어노테이션의 역할과 목적을 설명할 때는 '속성(Attribute)' 또는 '엘리먼트(Element)'라고 부르는 것이 더 정확하고 일반적입니다.



} // f end
@Controller
class TestClass2{
    // [2] 지정한 코드에 어노테이션 주입하기.
    @Annotation2( value1 = "유재석" , value2 =  40 )
    public void method1(){  }
        // 어노테이션 자체는 메소드 내부 로직에 직접 영향을 주지 않는다.
        // 대신, '리플렉션'을 통해 이 메소드에 접근하는 외부 코드에서
        // value1, value2 값을 읽어 특정 작업을 수행할 수 있다.

        // 핵심: 어노테이션 값("유재석", 40)은 메소드 내에서 지역 변수처럼 바로 사용할 수 없습니다.
        // 어노테이션은 코드에 붙이는 '메타데이터(metadata)' 또는 **'꼬리표'**입니다.
        // 이 꼬리표 정보는 **리플렉션(Reflection)**이라는 기술을 통해 별도의 코드에서 읽어 들여 활용하는 방식입니다.

    @Annotation2( value1 = "강호동"  ) // value2 생략시 기본값 대입
    public void method2() { }
}

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE ) // (클래스,인터페이스,열거형) 타입에 대상에 적용 하겠다고 설정
@interface Annotation3{
    String value1();
    int value2() default 100;
} // i end

@Annotation3( value1 = "유재석" , value2 = 40 ) // 아래 클래스에 어노테이션 주입
class Member{ }


public class Example1 {

    public static void main(String[] args) {
        Annotation3 annotation3  = Member.class.getAnnotation( Annotation3.class );
        System.out.println( annotation3.value1() );  // 유재석
        System.out.println( annotation3.value2() );  // 40
    }
}
