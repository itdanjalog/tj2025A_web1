package example.day08._2MVC.model.dao;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository // [1] Repository 빈 등록
public class MvcDao extends Dao { // extends Dao 할 경우 db연동 상속받아 사용한다.
    // 다음 레이어(계층) 없어서 @AutoWired 없다.
    public void method(){
        System.out.println("MvcDao.method");
        try{
            String sql ="select * from mvc";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                System.out.println( rs.getString("var1"));
            }
        } catch (Exception e) { System.out.println(e);   }
    }
}
