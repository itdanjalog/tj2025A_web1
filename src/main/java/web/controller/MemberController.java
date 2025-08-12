package web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MemberDto;
import web.service.MemberService;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired private MemberService memberService;

    // [1] 회원가입
    // [POST] http://localhost:8080/member/signup
    // Content-Type : application/json
    // body : { "mid" : "qwe" , "mpwd":"1234" , "mname":"유재석" , "mphone" : "010-0000-0000" }
    //  vs
    // Content-Type : multipart/form-data
    @PostMapping("/signup")
    //public boolean sigunUp( @RequestBody MemberDto memberDto  ){ // JSON 사용시 @RequestBody명시
    public int sigunUp( @RequestBody MemberDto memberDto  ){ // multipart/form-data 사용시 @RequestBody생략
        System.out.println("MemberController.sigunUp");
        System.out.println("memberDto = " + memberDto);
        //return false;
        int result = memberService.sigunUp( memberDto );
        System.out.println("result = " + result);
        return result;
    }

    // [2] 로그인 + 세션 생성 (서버내 저장소 = 주로 로그인된 회원정보 ) :  HttpServletRequest req
    @PostMapping("/login")
    //  [POST]  http://localhost:8080/member/login
    // Content-Type : application/json
    // body : { "mid" : "qwe" , "mpwd":"1234" }
    public int login(@RequestBody MemberDto memberDto , HttpServletRequest req ){
        System.out.println("MemberController.login");
        System.out.println("memberDto = " + memberDto);
        int result = memberService.login( memberDto );
        if( result != 0 ){
            HttpSession session = req.getSession(); // - 세션 호출
            session.setAttribute("loginMno" , result ); // 세션 객체내 새로운 속성 추가 , 로그인성공한 결과를 'loginDto' 라는 이름으로 저장.
            session.setMaxInactiveInterval( 60 * 10 ); // 세션 유지 시간[초] : 60*10 => 10분
        }  // else end
        return result; // 로그인 성공처리
    } // f end


    // [3] 로그인 상태 확인 , 내정보보기(마이페이지)
    // [GET] http://localhost:8080/member/info
    @GetMapping("/info")
    public MemberDto info( HttpServletRequest request ){
        HttpSession session = request.getSession();// 1. 세션호출
        if( session == null  || session.getAttribute("loginMno") == null ) return null; // 2. 만약에 세션이 존재하지 않으면 null 반환
        Object object = session.getAttribute("loginMno"); // 3. 로그인 성공시 저장한 loginDto 의 로그인정보를 꺼낸다.
        int loginMno = (int)object; // 4. 세션에 저장된 자료들은 모두 Object 타입 이므로 타입변환한다.

        MemberDto memberDto = memberService.info( loginMno );

        return memberDto; // 5. 로그인된 정보 반환
    }


    // [4] 로그아웃 + 세션 삭제
    // [GET] http://localhost:8080/member/logout
    @GetMapping("/logout")
    public boolean logout( HttpServletRequest request ){
        HttpSession session = request.getSession();// 1. 세션 호출
        if( session == null  || session.getAttribute("loginMno") == null ) return false;
        //session.invalidate(); // 2. 세션내 전체 속성 초기화 한다.
        session.removeAttribute("loginMno"); // 2. 세션내 특정 속성만 초기화 한다.
        return true;
    } // f end

    // [A] 아이디 중복 검사 -> 중복검사 로 호환
    // [GET] http://localhost:8080/member/checkid?mid=sample
    // 2025-08-13
    @GetMapping("/checkid")
    public boolean checkId(@RequestParam String mid , @RequestParam String type ) {
        return memberService.checkId(mid , type ); // true=이미 존재 / false=사용 가능
    }


    // [U] 회원수정 (이름/연락처)
    // [PUT] http://localhost:8080/member
    // Body(JSON): { "mname": "새이름", "mphone": "010-1234-5678" }
    // 2025-08-13
    @PutMapping
    public boolean updateMember(@RequestBody MemberDto body, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginMno") == null) return false;

        int loginMno = (int) session.getAttribute("loginMno");

        // 세션의 회원번호로 강제 매핑하여 본인만 수정 가능
        MemberDto dto = new MemberDto();
        dto.setMno(loginMno);
        dto.setMname(body.getMname());
        dto.setMphone(body.getMphone());

        return memberService.updateMember(dto);
    }

    // [D] 회원탈퇴 (본인)
    // [DELETE] http://localhost:8080/member
    // 2025-08-13
    @DeleteMapping
    public boolean deleteMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginMno") == null) return false;

        int loginMno = (int) session.getAttribute("loginMno");
        boolean ok = memberService.deleteMember(loginMno);

        // 성공 시 세션 정리
        if (ok) {
            session.removeAttribute("loginMno");
        }
        return ok;
    }

    // [P] 비밀번호 변경 (본인만)
    // [PUT] http://localhost:8080/member/password
    // Body(JSON): { "currentPwd": "1234", "newPwd": "abcd1234" }
    // 2025-08-13
    @PutMapping("/password")
    public boolean changePassword(@RequestBody Map<String, String> body, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginMno") == null) return false;

        int loginMno = (int) session.getAttribute("loginMno");
        String currentPwd = body.get("currentPwd");
        String newPwd = body.get("newPwd");

        if (currentPwd == null || newPwd == null || newPwd.isBlank()) return false;

        return memberService.changePassword(loginMno, currentPwd, newPwd);
    }



}
