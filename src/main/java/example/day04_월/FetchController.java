package example.day04_월;

import org.springframework.web.bind.annotation.*;

@RestController
public class FetchController {

    @GetMapping("/day04/exam1")
    public void method2(){System.out.println("FetchController.method2");
    }

    @PostMapping("/day04/exam1")
    public void method1(){System.out.println("FetchController.method1");
    }

    @PutMapping("/day04/exam1")
    public void method3(){System.out.println("FetchController.method3");
    }
    @DeleteMapping("/day04/exam1")
    public void method4(){System.out.println("FetchController.method4");
    }


    // ======================================================== //
    @GetMapping("/day04/exam2")
    public void method5( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method5");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PostMapping("/day04/exam2")
    public void method6( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method6");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PutMapping("/day04/exam2")
    public void method7( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method7");
        System.out.println("name = " + name + ", age = " + age);
    }

    @DeleteMapping("/day04/exam2")
    public void method8( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method8");
        System.out.println("name = " + name + ", age = " + age);
    }

    // ======================================================== //
    @PostMapping("/day04/exam3")
    public void method9( @RequestBody ){
    }

    @PutMapping("/day04/exam3")
    public void method10( @RequestBody ){
    }

    // ======================================================== //
    @GetMapping("/day04/exam2")
    public void method5( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method5");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PostMapping("/day04/exam2")
    public void method6( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method6");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PutMapping("/day04/exam2")
    public void method7( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method7");
        System.out.println("name = " + name + ", age = " + age);
    }

    @DeleteMapping("/day04/exam2")
    public void method8( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method8");
        System.out.println("name = " + name + ", age = " + age);
    }


}
