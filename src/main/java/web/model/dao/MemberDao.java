package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.MemberDto;

import java.sql.*;

@Repository
public class MemberDao extends Dao {

    // [1]. 회원가입 SQL 처리 메소드
    public int sigunUp( MemberDto memberDto ) {
        try {
            // [1] SQL 작성한다.
            String sql ="insert into member( mid , mpwd , mname , mphone  ) values( ? , ? , ? , ? )";
            // [2] DB와 연동된 곳에 SQL 기재한다.
            PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1 , memberDto.getMid() );
            ps.setString( 2 , memberDto.getMpwd() );
            ps.setString( 3 , memberDto.getMname() );
            ps.setString( 4 , memberDto.getMphone() );
            // [3] 기재된 SQL를 실행하고 결과를 받는다. .
            int count = ps.executeUpdate();
            // [4] 결과에 따른 처리 및 반환를 한다.
            if( count == 1 ) {
                ResultSet rs = ps.getGeneratedKeys();
                if( rs.next() ) {
                    int mno = rs.getInt( 1 );
                    return mno; // 회원가입 성공후 등록한 회원번호 반환
                }
            }
        }catch( SQLException e ) { System.out.println( e ); }
        return 0; // 회원가입 실패시 0 반환
    } // f end

    // [2]. 로그인 SQL 처리 메소드
    public int login( MemberDto memberDto ) {
        try {
            // [1] SQL 작성한다.
            String sql = "select mno from member where mid = ? and mpwd = ? ";
            // [2] DB와 연동된 곳에 SQL 기재한다.
            PreparedStatement ps =  conn.prepareStatement(sql);
            // [*] 기재된 SQL 에 매개변수 값 대입한다.
            ps.setString( 1 , memberDto.getMid() );
            ps.setString( 2 , memberDto.getMpwd() );
            // [3] 기재된 SQL 실행하고 결과를 받는다.
            ResultSet rs = ps.executeQuery();
            // [4] 결과에 따른 처리 및 반환를 한다.
            if( rs.next() ) {
                int mno = rs.getInt("mno");
                return mno; // - 0초과이면 로그인성공한 회원번호
            }
        }catch( SQLException e ) { System.out.println( e ); }
        return 0; // - 0 이면 로그인 실패
    } // f end

    // [4]. 내정보 보기 SQL 처리 메소드
    public MemberDto info( int loginMno ) {
        try {
            String sql ="select * from member where mno = ? "; // [1] SQL 작성한다.
            PreparedStatement ps = conn.prepareStatement(sql); // [2] DB와 연동된 곳에 SQL 기재한다.
            ps.setInt(  1 , loginMno); // [*] 기재된 SQL 에 매개변수 값 대입한다.
            ResultSet rs = ps.executeQuery(); // [3] 기재된 SQL 실행하고 결과를 받는다.
            if( rs.next() ) { // [4] 결과에 따른 처리 및 반환를 한다.
                MemberDto memberDto = new MemberDto();
                memberDto.setMno( rs.getInt("mno") );
                memberDto.setMid( rs.getString("mid") );
                memberDto.setMname( rs.getString("mname" ) );
                memberDto.setMphone( rs.getString("mphone") );
                memberDto.setMdate( rs.getString("mdate") );
                return memberDto; // 조회된 회원정보를 반환한다.
            }
        }catch(SQLException e ) { System.out.println(e);}
        return null; // 조회된 회원정보가 없을때. null 반환한다
    } // f end

    // [A] 아이디 중복 검사: 존재하면 true, 없으면 false
    // 2025-08-13
    public boolean checkId(String mid , String type) {
        String sql = "SELECT 1 FROM member WHERE "+type+" = ? ";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mid);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 한 행이라도 나오면 존재
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [U] 회원수정: 이름/연락처 수정 (본인 기준: mno)
    // 2025-08-13
    public boolean updateMember(MemberDto memberDto) {
        String sql = "UPDATE member SET mname = ?, mphone = ? WHERE mno = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, memberDto.getMname());
            ps.setString(2, memberDto.getMphone());
            ps.setInt(3, memberDto.getMno());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [D] 회원탈퇴: 본인 기준: mno
    // 2025-08-13
    public boolean deleteMember(int mno) {
        String sql = "DELETE FROM member WHERE mno = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, mno);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [P] 비밀번호 변경 (현재 비번 검증 + 원자적 업데이트)
    // 2025-08-13
    public boolean updatePassword(int mno, String currentPwd, String newPwd) {
        final String sql = "UPDATE member SET mpwd = ? WHERE mno = ? AND mpwd = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPwd);
            ps.setInt(2, mno);
            ps.setString(3, currentPwd);
            int count = ps.executeUpdate();
            return count == 1; // 현재 비번 일치 & 업데이트 성공
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

}
