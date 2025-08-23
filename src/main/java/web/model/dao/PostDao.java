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

    // [2-1] 게시물 전체 개수 조회
    public int getTotalSize(int cno) {
        try {
            String sql = "select count(*) from post where cno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    // [2-2] 게시물 전체 조회
    public List<PostDto> findAll(int cno, int startRow, int display) {
        List<PostDto> list = new ArrayList<>();
        try {
            String sql = "select * from post p inner join member m on p.mno = m.mno where p.cno = ? order by p.pno desc limit ?, ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cno);
            ps.setInt(2, startRow);
            ps.setInt(3, display);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostDto postDto = PostDto.builder()
                        .pno(rs.getInt("pno"))
                        .ptitle(rs.getString("ptitle"))
                        .pcontent(rs.getString("pcontent"))
                        .pdate(rs.getString("pdate"))
                        .pview(rs.getInt("pview"))
                        .mno(rs.getInt("mno"))
                        .cno(rs.getInt("cno"))
                        .mid(rs.getString("mid"))
                        .build();
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
                return PostDto.builder()
                        .pno(rs.getInt("pno"))
                        .ptitle(rs.getString("ptitle"))
                        .pcontent(rs.getString("pcontent"))
                        .pdate(rs.getString("pdate"))
                        .pview(rs.getInt("pview"))
                        .mno(rs.getInt("mno"))
                        .cno(rs.getInt("cno"))
                        .mid(rs.getString("mid"))
                        .cname(rs.getString("cname"))
                        .build();
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
