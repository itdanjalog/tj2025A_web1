package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dto.MemberDto;

@Service
public class MemberService {

    @Autowired private MemberDao memberDao;


    // [1] 회원가입
    public int sigunUp( MemberDto memberDto ){
        System.out.println("MemberService.sigunUp");
        System.out.println("memberDto = " + memberDto);
        int result = memberDao.sigunUp(memberDto);
        System.out.println("result = " + result);
        return result;
    }

    // [2] 로그인
    public int login( MemberDto memberDto ){
        System.out.println("MemberService.login");
        System.out.println("memberDto = " + memberDto);
        // (4) 로그인에서 입력한 아이디와 비밀번호가 모두 일치하면 회원정보 가져오기
        int result = memberDao.login(  memberDto );
        return result;
    }

    // [2] 로그인
    public MemberDto info( int loginMno ) {
        // (4) 로그인에서 입력한 아이디와 비밀번호가 모두 일치하면 회원정보 가져오기
        MemberDto result = memberDao.info(  loginMno );
        return result;
    }

    // [A] 아이디 중복 검사(true=이미 존재 / false=사용 가능)
    // 2025-08-13
    public boolean checkId(String mid , String type ) {
        return memberDao.checkId(mid , type);
    }


    // [U] 회원수정(이름/연락처)
    // 2025-08-13
    public boolean updateMember(MemberDto memberDto) {
        // 필요한 경우: 존재 여부나 권한 체크 로직 추가 가능
        return memberDao.updateMember(memberDto);
    }

    // [D] 회원탈퇴
    // 2025-08-13
    public boolean deleteMember(int mno) {
        return memberDao.deleteMember(mno);
    }

    // [P] 비밀번호 변경
    // 2025-08-13
    public boolean changePassword(int mno, String currentPwd, String newPwd) {
        // 필요 시: newPwd 유효성(길이/복잡도) 검증 추가 가능
        return memberDao.updatePassword(mno, currentPwd, newPwd);
    }

}
