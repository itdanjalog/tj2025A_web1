package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.PostDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDao extends Dao {
    // [1] 게시물등록
    public int writePost(PostDto postDto ){
        try{ String sql = "insert into post( ptitle, pcontent, cno, mno)values(?,?,?,?)";
            // Statement.RETURN_GENERATED_KEYS : SQL 실행시 자동 생성된 **PK 반환 설정**
            PreparedStatement ps = conn.prepareStatement( sql , Statement.RETURN_GENERATED_KEYS );
            // SQL문법내 '?'가 4개 이니까 setXXX 이용한 각 '?' 4개를 순서에 맞춰서 매개변수 대입
            ps.setString( 1, postDto.getPtitle() );
            ps.setString( 2, postDto.getPcontent() );
            ps.setInt( 3 , postDto.getCno() );
            ps.setInt( 4 , postDto.getMno() );
            int count = ps.executeUpdate();
            if( count == 1 ){
                ResultSet rs =  ps.getGeneratedKeys(); // SQL 실행후 자동 생성된 **PK 반환**
                if( rs.next() ) return rs.getInt( 1 ); // 등록 성공시 첫번째 pk속성 값 반환
            }
        } catch (Exception e) {  System.out.println(e); }
        return 0; // 등록 실패시
    } // func end

    // [2-1] 카테고리별 게시물 수
    public int getTotalCount( int cno  ){
        try{ String sql = "select count(*) from post where cno = ? "; // count(*) : 레코드 전체수를 반환하는 함수
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , cno );
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){return rs.getInt( 1 ); }// 첫번째 레코드의 속성값 1개 반환
        } catch (Exception e) {     System.out.println(e);     }
        return 0; // 조회 결과 없으면 0 반환
    } // func end

    // [2-2] 카테고리별 전체 게시물 정보 조회
    public List<PostDto> findAll( int cno , int startRow , int count ){
        List<PostDto> list = new ArrayList<>();
        try{ String sql = "SELECT * FROM post p INNER JOIN member m " +
                            " ON p.mno = m.mno WHERE p.cno = ?" +
                            " ORDER BY p.pno DESC LIMIT ? , ? ";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , cno );
            ps.setInt( 2 , startRow );
            ps.setInt( 3 , count );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                PostDto postDto = new PostDto();
                postDto.setMno( rs.getInt("mno") );             postDto.setCno( rs.getInt("cno") );
                postDto.setPcontent( rs.getString("pcontent")); postDto.setPdate( rs.getString("pdate") );
                postDto.setPview( rs.getInt("pview") );         postDto.setPno( rs.getInt( "pno") );
                postDto.setPtitle( rs.getString("ptitle"));
                postDto.setMid( rs.getString( "mid" )); // member 테이블과 join한 결과 mid 호출 가능하다.
                list.add( postDto );
            }
        } catch (Exception e) {  System.out.println(e);}
        return list;
    } // func end

    // [2-3] 카테고리별 *검색* 게시물 수
    public int getTotalCountSearch( int cno , String key , String keyword ){
        try{// 1. SQL 작성
            String sql =" select count(*) from post where cno = ?";
            // 2. key(속성명) 에 따른 (동적) sql 추가
            if( key.equals("ptitle") ){
                sql += " and ptitle like ? "; // 안뒤로 뛰어쓰기 꼭 넣기
            }else if( key.equals("pcontent") ){
                sql += " and pcontent like ? ";
            } // 그외 검색 속성이 존재하면 추가한다
            System.out.println( "[확인]동적SQL : " + sql );
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , cno );
            ps.setString( 2 , "%"+keyword+"%" ); // SQL 에서 포함된 비교는 LIKE %키워드% 사용된다.
            ResultSet rs = ps.executeQuery();
            if( rs.next() ) return rs.getInt(1);
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }

    // [2-4] 카테고리별 *검색* 전체 게시물 정보 조회
    public List<PostDto> findAllSearch( int cno , int startRow , int count , String key , String keyword ){
        List<PostDto> list = new ArrayList<>();
        try{ String sql = " select * from post p inner join member m on p.mno = m.mno where cno = ? ";
            // **** 검색 SQL ****
            if( key.equals("ptitle") ){ sql += " and ptitle like ? "; }
            else if( key.equals("pcontent") ){ sql += " and pcontent like ? "; }
            // 그외(정렬/페이징)
            sql += " order by pno desc limit ? , ? ";       System.out.println( "[확인]동적SQL : " + sql );
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , cno );
            ps.setString( 2 , "%" + keyword +"%" );
            ps.setInt( 3 , startRow );           ps.setInt( 4 , count );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                PostDto postDto = new PostDto();
                postDto.setMno( rs.getInt("mno") );             postDto.setCno( rs.getInt( "cno" ));
                postDto.setPcontent( rs.getString("pcontent")); postDto.setPdate( rs.getString("pdate"));
                postDto.setPview( rs.getInt("pview"));          postDto.setPno( rs.getInt("pno"));
                postDto.setPtitle( rs.getString("ptitle"));     postDto.setMid( rs.getString("mid") );
                list.add( postDto );
            }
        } catch (Exception e) { System.out.println( e ); }
        return list;
    }

    // [3-1] 게시물 개별 정보 조회
    public PostDto getPost( int pno ){
        try{ String sql ="select * from post p inner join member m on p.mno = m.mno where pno = ? ";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , pno );
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                PostDto postDto = new PostDto();
                postDto.setMno( rs.getInt("mno") );             postDto.setCno( rs.getInt( "cno" ));
                postDto.setPcontent( rs.getString("pcontent")); postDto.setPdate( rs.getString("pdate"));
                postDto.setPview( rs.getInt("pview"));          postDto.setPno( rs.getInt("pno"));
                postDto.setPtitle( rs.getString("ptitle"));     postDto.setMid( rs.getString("mid") );
                return postDto;
            }
        } catch (Exception e) { System.out.println(e); }
        return null;
    }

    // [3-2] 게시물 조회수 1증가 + 업데이트
    public void incrementView( int pno ){
        try{ String sql = "update post set pview = pview + 1 where pno = ? ";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , pno );
            ps.executeUpdate(); // void 라서 return 없다.
        } catch (Exception e) { System.out.println(e); }
    }

    // [4]
    public boolean deletePost( int pno ){
        try{ String sql = "delete from post where pno = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , pno );
            return ps.executeUpdate() == 1 ;
        } catch (Exception e) { System.out.println(e); }
        return false;
    }

    // [5]
    public int updatePost( PostDto postDto ){
        try{ String sql = "update post set ptitle = ? , pcontent = ? , cno = ? where pno = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString( 1 , postDto.getPtitle() );
            ps.setString( 2 , postDto.getPcontent() );
            ps.setInt( 3 , postDto.getCno() );
            ps.setInt( 4 , postDto.getPno() );
            int count = ps.executeUpdate();
            if( count == 1 ){ return postDto.getPno(); }
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }

} // class end












