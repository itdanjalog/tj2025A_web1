package example.day08._2MVC.controller;

import example.day08._2MVC.service.MvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MvcController {
    @Autowired
    private MvcService mvcService;
    @GetMapping("/day08/mvc")
    public void method(){
        System.out.println("MvcController.method");
        mvcService.method();
    }
}
