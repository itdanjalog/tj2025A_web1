package example.day06;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchController {
    // (1)
    @GetMapping("/day06/exam1")
    public boolean method1(){
        return true; // 임의 샘플
    }
}
