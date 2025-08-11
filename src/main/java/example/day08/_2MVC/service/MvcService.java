package example.day08._2MVC.service;

import example.day08._2MVC.model.dao.MvcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // [1] Service 빈 등록
public class MvcService {
    @Autowired // [2] Repository(dto) 빈 주입
    private MvcDao mvcDao;

    public void method(){
        System.out.println("MvcService.method");
        mvcDao.method(); // 리포지토리(다오)의 메소드 호출
    }

}







