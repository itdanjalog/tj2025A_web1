package example.day07;

import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private StudentDao(){ connect(); }

    @Getter
    private static final StudentDao instance = new StudentDao();

    private String db_url = "jdbc:mysql://localhost:3306/spring07";
    private String db_user = "root";
    private String db_password = "1234";
    private Connection conn;

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( db_url , db_user , db_password );
        }catch (Exception e ){ System.out.println(e);   }
    }

    // 1.
    public boolean save( StudentDto studentDto ){
        try{
            String sql = "insert into student(sname,skor,seng,smath)values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString( 1 , studentDto.getSname() );
            ps.setInt( 2 , studentDto.getSkor() );
            ps.setInt( 3 , studentDto.getSeng() );
            ps.setInt( 4 , studentDto.getSmath() );
            int count = ps.executeUpdate();
            if( count == 1 ) return true;
        }catch (Exception e ){
            System.out.println(e);
        }
        return false ;
    }
    // 2.
    public List<StudentDto> findAll(){
        List<StudentDto> list = new ArrayList<>();
        try{
            String sql = "select * from student";
            PreparedStatement ps = conn.prepareStatement( sql );
            ResultSet rs =  ps.executeQuery();
            while ( rs.next() ){
                StudentDto studentDto = new StudentDto();
                studentDto.setSdate( rs.getString("sdate") );
                studentDto.setSeng( rs.getInt("seng") );
                studentDto.setSkor( rs.getInt("skor"));
                studentDto.setSmath( rs.getInt("smath"));
                studentDto.setSno( rs.getInt("sno"));
                studentDto.setSname( rs.getString("sname"));
                list.add( studentDto );
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    }
    // 3. 
    public boolean delete( int sno ){

        try{
            String sql = "delete from student where sno = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , sno );
            int count = ps.executeUpdate();
            if( count == 1 ){
                return true;
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return false;
    }

}



