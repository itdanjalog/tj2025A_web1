package example.day07;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    // 싱글톤
    @Getter // 롬복: 해당하는 멤버변수에만 getter 제공받을수 있다.
    private static  final StudentDao instance = new StudentDao();
    private StudentDao( ){ connect(); }
    // DB 정보
    private String db_url = "jdbc:mysql://localhost:3306/spring07";
    private String db_user = "root";
    private String db_password = "1234";
    private Connection conn;
    // 연동 코드
    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( db_url , db_user , db_password );
        }catch (Exception e ){      System.out.println(e);  }
    } // 연동 end

    // 1. 등록 : Controller 로부터 studentDto를 매개변수로 받아서 처리후 boolean(true/false) 반환 메소드
    public boolean save( StudentDto studentDto ){
        try{ // 예외처리
            String sql ="insert into student(sname,skor,smath) values(?,?,?)"; // 1) SQL 작성한다.
            PreparedStatement ps = conn.prepareStatement(sql); // 2) 연동된 DB에 작성한 SQL 를 기재한다.
            ps.setString( 1 , studentDto.getSname()  ); // 3) 기재된 SQL에 매개변수 대입
            ps.setInt( 2 , studentDto.getSkor() );          // 1,2,3 : SQL문법내 ? 순서번호
            ps.setInt( 3 , studentDto.getSmath() );         // .set타입() :
            // SQL문법내 3(세번쨰) ? 에 수학점수를 int타입 으로 대입한다.
            int count = ps.executeUpdate(); // 4) 기재된 SQL 실행한다. INSERT/UPDATE/DELETE -> executeUpdate , SELECT -> executeQuery
            if( count == 1 ) return true; // 5) 결과 , 1개 저장 했으면 성공
        } catch (Exception e) { System.out.println(e); }
        return false; // 아니면 실패
    } // m end

    // 2. 전제조회
    public List<StudentDto> find(){
        List<StudentDto> list = new ArrayList<>(); // 여러개 dto 담을 준비!!!!!!!!!!!!!!!!!!
        try{
            String sql = "select * from student ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // 4) 기재된 SQL 실행한다. 실행 결과의 모든 레코드를 (ResultSet) 인터페이스 조작
            // rs.next() : SQL 조회 결과중 다음 레코드 이동 함수 , 주로 하나조회 : if( rs.next() ) , 다수조회 : while( rs.next() )
            // rs.get타입( 필드명혹은번호 ) : 현재 조회중인 레코드의 필드값 호출
            while ( rs.next() ){
                StudentDto studentDto = new StudentDto( // *******하나의 레코드를 DTO로 준비*******
                        rs.getInt( 1 ) , // 현재 조회중인 레코드의 (1번)첫번째 필드/속성/제목/열/컬럼 = 회원번호
                        rs.getString("sname"), // 현재 조회중인 레코드의 "sname" 이라는 필드/속성/제목/열/컬럼 = 회원명
                        rs.getInt("skor"), rs.getInt("smath"),
                        rs.getString("sdate") // dateTime 은 String 타입 으로 가져오기.
                );
                list.add( studentDto ); // 하나의 DTO를 리스트에 대입한다.
            }
        } catch (Exception e) { System.out.println( e ); }
        return list;
    } // class end

} // class end




















