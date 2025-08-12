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


        // --- HttpSession 주요 메서드 예시 출력 ---
        System.out.println("▶ 세션 ID: " + session.getId());
        // 브라우저마다 고유하게 부여되는 세션 식별자

        System.out.println("▶ 세션 생성 시간(ms): " + session.getCreationTime());
        // 세션이 처음 생성된 시각 (epoch time, ms 단위)

        System.out.println("▶ 세션 마지막 접근 시간(ms): " + session.getLastAccessedTime());
        // 사용자가 마지막으로 이 세션을 사용한 시각

        System.out.println("▶ 세션 최대 유효 시간(초): " + session.getMaxInactiveInterval());
        // 마지막 요청 이후 세션이 유지되는 최대 시간 (초)

        // session.invalidate();
        // 세션 무효화 - 로그아웃 시 주로 사용

        // session.isNew();
        // 새로 생성된 세션인지 여부 (true/false)


        return true;
    }

    @GetMapping("/info") // 2025-08-12: 현재 세션 조회 - 세션이 없으면 401
    public String me(  HttpServletRequest  request ) {
        HttpSession session = request.getSession();
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
