package example.day08._2MVC.model.dao;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class MvcDao extends Dao {

    public void method(){
        System.out.println("MvcDao.method");
        try{
            String sql = "select * from mvc";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs =  ps.executeQuery();
            while (rs.next()){
                System.out.println( rs.getString("var1"));
            }
        }catch (Exception e ){
            System.out.println(e);
        }
    }
}
