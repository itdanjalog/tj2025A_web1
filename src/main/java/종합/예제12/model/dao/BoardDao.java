package 종합.예제12.model.dao;

import org.springframework.stereotype.Repository;
import 종합.예제12.model.dto.BoardDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardDao extends Dao  {

    // [1] 등록
    public boolean boardWrite(BoardDto boardDto ){
        System.out.println("BoardDao.boardWrite");
        System.out.println("boardDto = " + boardDto);
        try{
            String sql = "insert into board(bcontent,bwriter)values(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString( 1 , boardDto.getBcontent() );
            ps.setString( 2 , boardDto.getBwriter() );
            int count = ps.executeUpdate();
            if( count == 1 ) return true;
        } catch (Exception e) { System.out.println(e); }
        return false;
    }

    // [2] 전체조회
    public List<BoardDto> boardPrint(){
        System.out.println("BoardDao.boardPrint");
        List<BoardDto> list = new ArrayList<>(); // 1. 여러개 레코드를 dto로 변환해서 dto들을 저장할 리스트 선언
        try{
            String sql = "select * from board"; // 2. sql 작성
            PreparedStatement ps = conn.prepareStatement(sql); // 3. sql기재
            ResultSet rs = ps.executeQuery(); // 4. sql 실행 후 결과 조작
            while ( rs.next() ){ // 5. rs.next() 조회된결과에서 다음 레코드 이동
                BoardDto boardDto = new BoardDto();
                boardDto.setBno( rs.getInt("bno") ); // 6. rs.getInt() 현재조회중인 레코드에서 bno속성값 호출
                boardDto.setBcontent( rs.getString("bcontent"));
                boardDto.setBwriter( rs.getString("bwriter"));
                list.add( boardDto ); // 7. 생성한 dto를 리스트에 저장
            }
        } catch (Exception e) {  System.out.println(e);}
        return list; // 8. 리스트 반환
    }

    // [3] 개별조회
    public BoardDto boardFind( int bno ){
        System.out.println("BoardDao.boardFind");
        System.out.println("bno = " + bno);
        try{
            String sql = "select * from board where bno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt( 1  , bno ); // sql문법내 첫번째 ? 에 int타입 bno값 대입
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                // while(rs.next()) : 여러개 조회
                // if( rs.next() ) : 한개 조회
                BoardDto boardDto = new BoardDto();
                boardDto.setBno( rs.getInt( 1 ) );
                boardDto.setBcontent( rs.getString( 2 ) );
                boardDto.setBwriter( rs.getString( 3 ) );
                return boardDto; // 성공시 1개 dto
            }
        } catch (Exception e) {System.out.println(e); }
        return null; // 실패시 null
    }

    // [4] 개별삭제
    public boolean boardDelete( int bno ){
        System.out.println("BoardDao.boardDelete");
        System.out.println("bno = " + bno);
        try{
            String sql ="delete from board where bno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt( 1 , bno );
            int count = ps.executeUpdate();
            if( count == 1 ) return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // [5] 개별수정
    public boolean boardUpdate( BoardDto boardDto ){
        try{
            String sql ="update board set bcontent = ? where bno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString( 1 , boardDto.getBcontent() );
            ps.setInt( 2 , boardDto.getBno() );
            int count = ps.executeUpdate();
            if( count == 1 ) return true;
        } catch (Exception e) {  System.out.println(e);   }
        return false;
    }

} // CLASS END























