package example.day04_월;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class FetchController {

    @GetMapping("/day04/exam1")
    public void method2(){System.out.println("FetchController.method1");
    }

    @PostMapping("/day04/exam2")
    public void method1(){System.out.println("FetchController.method2");
    }

    @PutMapping("/day04/exam3")
    public void method3(){System.out.println("FetchController.method3");
    }
    @DeleteMapping("/day04/exam4")
    public void method4(){System.out.println("FetchController.method4");
    }


    // ======================================================== //
    @GetMapping("/day04/exam5")
    public void method5( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method5");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PostMapping("/day04/exam6")
    public void method6( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method6");
        System.out.println("name = " + name + ", age = " + age);
    }

    @PutMapping("/day04/exam7")
    public void method7( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method7");
        System.out.println("name = " + name + ", age = " + age);
    }

    @DeleteMapping("/day04/exam8")
    public void method8( @RequestParam String name , @RequestParam int age ){
        System.out.println("FetchController.method8");
        System.out.println("name = " + name + ", age = " + age);
    }

    // ======================================================== //
    @PostMapping("/day04/exam9")
    public void method9(@RequestBody Map< String,String> map ){
        System.out.println("FetchController.method9");
        System.out.println("map = " + map);
    }

    @PutMapping("/day04/exam10")
    public void method10( @RequestBody TaskDto taskDto ){
        System.out.println("FetchController.method10");
        System.out.println("taskDto = " + taskDto);
    }

    // ======================================================== //
    @GetMapping("/day04/exam11")
    public boolean method11(  ){
        System.out.println("FetchController.method11");
        return true ;
    }

    @PostMapping("/day04/exam12")
    public int method12( ){
        System.out.println("FetchController.method12");
        return 30;
    }

    @PutMapping("/day04/exam13")
    public TaskDto method13( ){
        System.out.println("FetchController.method13");
        TaskDto taskDto = new TaskDto("유재석", 20 );
        return taskDto;
    }

    @DeleteMapping("/day04/exam14")
    public ArrayList<TaskDto> method14(  ){
        System.out.println("FetchController.method14");
        ArrayList<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add( new TaskDto( "유재석" , 20 ) );
        taskDtos.add( new TaskDto( "강호동" , 40 ) );
        return taskDtos;
    }


}
