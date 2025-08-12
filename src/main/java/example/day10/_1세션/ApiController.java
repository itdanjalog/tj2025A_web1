// 작성일: 2025-08-12
package example.day10._1세션;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    @PostMapping("/login") // 2025-08-12: 세션 로그인 - username을 세션에 저장
    public boolean login(  @RequestBody LoginDto loginDto, HttpServletRequest  request ) {
        HttpSession session = request.getSession();

        // 아이디와 비밀번호가 일치하면 회원번호 저장
        int loginMno = loginDto.getMno();
        session.setAttribute("loginMno", loginMno );

        return true;
    }

    @GetMapping("/info") // 2025-08-12: 현재 세션 조회 - 세션이 없으면 401
    public String me(  HttpServletRequest  request ) {
        HttpSession session = request.getSession();



        // --- 세션 기본 정보 ---
        System.out.println("▶ 세션 ID: " + session.getId());
        System.out.println("▶ 세션 생성 시간(ms): " + session.getCreationTime());
        System.out.println("▶ 세션 마지막 접근 시간(ms): " + session.getLastAccessedTime());
        System.out.println("▶ 세션 최대 유효 시간(초): " + session.getMaxInactiveInterval());

        // --- 브라우저 & 네트워크 정보 ---
        String userAgent = request.getHeader("User-Agent");
        System.out.println("▶ User-Agent: " + userAgent);
        // 예: Mozilla/5.0 (Windows NT 10.0; Win64; x64) ...

        String clientIp = request.getRemoteAddr();
        System.out.println("▶ 클라이언트 IP: " + clientIp);
        // 로컬 테스트 시 127.0.0.1 또는 ::1

        String clientHost = request.getRemoteHost();
        System.out.println("▶ 클라이언트 Host: " + clientHost);

        int clientPort = request.getRemotePort();
        System.out.println("▶ 클라이언트 Port: " + clientPort);

        // session.invalidate();
        // 세션 무효화 - 로그아웃 시 주로 사용

        // session.isNew();
        // 새로 생성된 세션인지 여부 (true/false)




        System.out.println( request.getRemoteAddr());
        if (session == null || session.getAttribute("loginMno") == null) {
            return null;
        }
        String loginInfo = (String)session.getAttribute("loginMno");
        return loginInfo;
    }

    @GetMapping("/logout") // 2025-08-12: 로그아웃 - 세션 무효화
    public boolean logout( HttpServletRequest  request ) {
        HttpSession session = request.getSession();
        if (session != null) {session.removeAttribute("loginMno"); return true; }
        return false;
    }

}
