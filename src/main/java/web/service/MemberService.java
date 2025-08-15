package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dto.MemberDto;

import java.util.Map;

@Service // 스프링 컨테이너(메모리) 빈(객체) 등록
public class MemberService {

    @Autowired // 스프링 컨테이너(메모리)에 등록된 빈 주입(꺼내) 받기
    private MemberDao memberDao;

    // [1] 회원가입
    public int signUp(MemberDto memberDto ){
        int result = memberDao.signUp( memberDto );
        return result;
    } // func end

    // [2] 로그인
    public int login ( MemberDto memberDto ){
        int result = memberDao.login( memberDto );
        return result;
    }

    // [4] 회원정보조회
    public MemberDto info( int mno ){
        MemberDto result = memberDao.info( mno );
        return result;
    }

    // [5] 특정한 필드/열/컬럼 의 값 중복/존재 확인
    public boolean check( String type , String data ){
        boolean result = memberDao.check( type , data );
        return result;
    }

    // [6] 회원정보 수정
    public boolean update( MemberDto memberDto ){
        boolean result = memberDao.update( memberDto );
        return result;
    }

    // [7] 회원비밀번호수정
    public boolean updatePassword( int mno , Map<String,String> map ){
        boolean result = memberDao.updatePassword( mno , map );
        return result;
    }
    // [8] 회원탈퇴
    public boolean delete( int mno , String oldpwd ){
        boolean result = memberDao.delete( mno , oldpwd );
        return result;
    }
} // class end













