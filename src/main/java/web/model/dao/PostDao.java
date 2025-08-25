package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.PostDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostDao extends Dao {

    // [1] 글쓰기
    public boolean write(PostDto postDto) {
        try {
            String sql = "insert into post( ptitle, pcontent, mno, cno ) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postDto.getPtitle());
            ps.setString(2, postDto.getPcontent());
            ps.setInt(3, postDto.getMno());
            ps.setInt(4, postDto.getCno());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

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
        try{ String sql = "select * from post where cno = ? order by pno desc limit ? , ? ";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , cno );
            ps.setInt( 2 , startRow );
            ps.setInt( 3 , count );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                PostDto postDto = new PostDto();
                postDto.setMno( rs.getInt("mno") );
                postDto.setCno( rs.getInt("cno") );
                postDto.setPcontent( rs.getString("pcontent"));
                postDto.setPdate( rs.getString("pdate") );
                postDto.setPview( rs.getInt("pview") );
                postDto.setPno( rs.getInt( "pno") );
                list.add( postDto );
            }
        } catch (Exception e) {  System.out.println(e);}
        return list;
    } // func end


    // [3-1] 검색된 게시물 수
    public int getTotalCountSearch(int cno, String key, String keyword) {
        try {
            // 1. 기본 SQL 작성
            String sql = "select count(*) from post where cno = ? ";

            // 2. key 값에 따라 동적으로 SQL에 검색 조건 추가
            if (key.equals("pcontent")) { // key가 'pcontent'이면
                sql += " and pcontent like ? ";
            } else if (key.equals("mno")) { // key가 'mno'(작성자)이면
                // mno는 보통 숫자 타입이므로 다른 테이블과 JOIN하여 회원 아이디로 검색해야 할 수 있습니다.
                // 여기서는 mno 필드에 직접 검색한다고 가정합니다.
                sql += " and mno like ? ";
            }
            // ... 다른 검색 조건이 있다면 else if 로 추가 가능

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cno);
            ps.setString(2, "%" + keyword + "%"); // 3. keyword가 포함된 레코드를 찾기 위해 %% 추가

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    // [3-2] 검색된 전체 게시물 정보 조회
    public List<PostDto> findAllSearch(int cno, int startRow, int count, String key, String keyword) {
        List<PostDto> list = new ArrayList<>();
        try {
            // 1. 기본 SQL 작성
            String sql = "select * from post where cno = ? ";

            // 2. key 값에 따라 동적으로 SQL에 검색 조건 추가
            if (key.equals("pcontent")) {
                sql += " and pcontent like ? ";
            } else if (key.equals("mno")) {
                sql += " and mno like ? ";
            }

            // 3. 정렬 및 페이징 SQL 추가
            sql += " order by pno desc limit ? , ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cno);
            ps.setString(2, "%" + keyword + "%"); // 4. keyword 값 설정
            ps.setInt(3, startRow);               // 5. 페이징 시작 위치
            ps.setInt(4, count);                  // 6. 페이지당 게시물 수

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostDto postDto = new PostDto();
                postDto.setMno(rs.getInt("mno"));
                postDto.setCno(rs.getInt("cno"));
                postDto.setPcontent(rs.getString("pcontent"));
                postDto.setPdate(rs.getString("pdate"));
                postDto.setPview(rs.getInt("pview"));
                postDto.setPno(rs.getInt("pno"));
                list.add(postDto);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }



    // [3] 게시물 개별 삭제
    public boolean delete(int pno) {
        try {
            String sql = "delete from post where pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // [4] 게시물 개별 수정
    public boolean update(PostDto postDto) {
        try {
            String sql = "update post set ptitle = ?, pcontent = ? , cno = ?  where pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postDto.getPtitle());
            ps.setString(2, postDto.getPcontent());
            ps.setInt(3, postDto.getCno());
            ps.setInt(4, postDto.getPno());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // [5] 게시물 개별 조회
    public PostDto findByPno(int pno) {
        try {
            String sql = "select * from post p "
                    + " inner join member m on p.mno = m.mno "
                    + " inner join category c on p.cno = c.cno "
                    + " where pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return null;
//                return PostDto.builder()
//                        .pno(rs.getInt("pno"))
//                        .ptitle(rs.getString("ptitle"))
//                        .pcontent(rs.getString("pcontent"))
//                        .pdate(rs.getString("pdate"))
//                        .pview(rs.getInt("pview"))
//                        .mno(rs.getInt("mno"))
//                        .cno(rs.getInt("cno"))
//                        .mid(rs.getString("mid"))
//                        .cname(rs.getString("cname"))
//                        .build();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // === 댓글 관련 DAO (추후 ReplyDao로 분리하는 것을 권장합니다) ===

    // [6] 댓글 쓰기 (요청에 따라 Map 사용)
    public boolean replyWrite(Map<String, String> map) {
        try {
            String sql = "insert into reply ( rcontent, pno, mno ) values( ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("rcontent"));
            ps.setString(2, map.get("pno"));
            ps.setString(3, map.get("mno"));
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // [7] 특정 게시물의 댓글 조회
    public List<Map<String, String>> replyFindAll(int pno) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            String sql = "select * from reply r inner join member m on r.mno = m.mno where r.pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("rno", rs.getString("rno"));
                map.put("rcontent", rs.getString("rcontent"));
                map.put("rdate", rs.getString("rdate"));
                map.put("mid", rs.getString("mid"));
                map.put("mno", rs.getString("mno"));
                list.add(map);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }


    /**
     * [NEW] 게시물 조회수 1 증가
     * @param pno 게시물 번호
     */
    public void incrementViewCount(int pno) {
        try {
            String sql = "update post set pview = pview + 1 where pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
