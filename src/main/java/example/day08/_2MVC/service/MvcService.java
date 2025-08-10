package example.day08._2MVC.service;

import example.day08._2MVC.model.dao.MvcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MvcService {
    @Autowired
    private MvcDao mvcDao;
    public void method(){
        System.out.println("MvcService.method");
        mvcDao.method();
    }
}
