package example.day07;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentDao studentDao = StudentDao.getInstance();

    // 1.
    // http://localhost:8080/student
    // { "sname" : "유재석" , "skor" : "10" , "seng" : "20" , "smath":"30" }
    @PostMapping("")
    public boolean save( @RequestBody StudentDto studentDto ){
        System.out.println("StudentController.save");
        System.out.println("studentDto = " + studentDto);
        boolean result = studentDao.save( studentDto );
        return  result;
    }

    // 2.
    // http://localhost:8080/student
    @GetMapping("")
    public List<StudentDto> findAll(){
        System.out.println("StudentController.findAll");
        List<StudentDto> result = studentDao.findAll();
        System.out.println("result = " + result);
        return  result;
    }
    
    // 3.
    // http://localhost:8080/student?sno=1
    @DeleteMapping("")
    public boolean delete( @RequestParam int sno ){
        System.out.println("StudentController.delete");
        System.out.println("sno = " + sno);
        boolean result = studentDao.delete( sno );
        return  result;
    }   

}
