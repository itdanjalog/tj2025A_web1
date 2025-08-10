package example.day08._1의존성;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
    [고전적인 방법1 : 인스턴스 생성 ]
    스프링 없이 객체(인스턴스)를 직접 생성하여 메소드를 사용한다.
    - 단점 : 강한 결합 으로 유지보수가 어려워진다.
*/
// [1] 서비스 클래스
class SampleService1{
    void method(){
        System.out.println("SampleService1.method"); // soutm
    } // f end
} // c end

// [2] 컨트롤러 클래스
class SampleController1{
    // 다른 클래스의 메소드를 호출하는 방법 ,
    SampleService1 sampleService1 = new SampleService1();
    public void method(){
        // 서비스클래스의 메소드 호출
        sampleService1.method();
    } // f end
} // cend

// ===================================================================
/*
    [고전적인 방법2 : 싱글톤 생성 ]
    스프링 없이 싱글톤(인스턴스)를 직접 생성하여 메소드를 사용한다.
    - 이유 : 프로그램내 하나의 인스턴스(객체) 가 하나만 필요로 할때 사용한다.
*/
// [1] 서비스 클래스
class SampleService2{
    //싱글톤 만들기
    private static final SampleService2 instance  = new SampleService2();
    private SampleService2(){}
    public static SampleService2 getInstance(){ return instance; }
    void method(){
        System.out.println("SampleService2.method");
    }
} // c end
// [2] 컨트롤러 클래스
class SampleController2{
    SampleService2 sampleService2 = SampleService2.getInstance();
    public void method(){
        sampleService2.method();
    }
}

// =========================================================================
/*
    [ 스프링 방법3 : IOC 와 DI ]
*/
// [1] 서비스 클래스
// bean : 스프링 컨테이너(메모리) 에서 관리하는 인스턴스/객체 , IOC
// IOC(제어의역전) : 개발자가 인스턴스(new) 생성하지 않고 스프링이 대신 인스턴스 생성/관리 한다.
@Component // 1.스프링 컨테이너(메모리)에 빈(인스턴스) 등록
class SampleService3{
    void method(){
        System.out.println("SampleService3.method");
    }
} // c end

// [2] 컨트롤러 클래스
class SampleController3{
    @Autowired // 2. 스프링 컨테이너(메모리)에 등록된 빈(인스턴스) 주입한다. D(Dependency)I(Injection)
    private SampleService3 sampleService3;

    public void method(){
        sampleService3.method();
    }
} // c end

// ===================================================================
/*
    [ 스프링 방법4 : IOC 와 DI ] *권장
*/
@Service // @Service 에는 @Component 포함
class SampleService4{
    void method(){
        System.out.println("SampleService4.method");
    }
} // c end

class SampleController4{
    // static : 전역키워드 , final : 수정불가키워드
    private final SampleService4 sampleService4;

    @Autowired // 생성자를 이용하여 빈 등록 하는 방법
    public SampleController4( SampleService4 sampleService4 ){
        this.sampleService4 = sampleService4;
    }

    public void method(){
        sampleService4.method();
    }
} // c end


public class Dependency {

}
