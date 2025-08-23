package web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.model.dto.PageDto;
import web.model.dto.PostDto;
import web.service.PostService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PostController
 * - @RestController: 이 클래스가 RESTful 컨트롤러임을 나타내며, 모든 메소드는 @ResponseBody를 포함합니다.
 * - @RequestMapping("/post"): 이 컨트롤러의 모든 요청 URL 앞에 "/post"가 기본으로 포함됩니다.
 * - @RequiredArgsConstructor: final 필드에 대한 생성자를 자동으로 생성하여 의존성 주입(DI)을 처리합니다. (Lombok)
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService; // 서비스 계층 의존성 주입

    /**
     * [1] 게시물 작성 컨트롤러
     * @param postDto { ptitle, pcontent, cno } JSON 객체
     * @param session 현재 로그인 세션
     * @return 성공 여부 (boolean)
     *
     * - @PostMapping: HTTP POST 요청을 처리합니다.
     * - @RequestBody: 요청 본문의 JSON 데이터를 PostDto 객체로 변환합니다.
     */
    @PostMapping
    public boolean writePost(@RequestBody PostDto postDto, HttpSession session) {
        System.out.println("PostController.writePost");
        System.out.println("postDto = " + postDto + ", session = " + session);
        // 1. 현재 로그인된 회원 번호를 세션에서 가져옵니다.
        Object loginMnoObject = session.getAttribute("loginMno");
        if (loginMnoObject == null) {
            return false; // 로그인되지 않은 경우 실패 처리
        }
        int loginMno = (Integer) loginMnoObject;

        // 2. PostDto에 회원 번호를 설정합니다.
        postDto.setMno(loginMno);

        // 3. 서비스 계층에 DTO를 전달하여 비즈니스 로직을 수행합니다.
        return postService.writePost(postDto);
    }

    /**
     * [2] 게시물 전체 조회 컨트롤러
     * @param cno 카테고리 번호
     * @param page 현재 페이지 번호
     * @return 페이징 처리된 게시물 목록 (PageDto)
     *
     * - @GetMapping: HTTP GET 요청을 처리합니다.
     * - @RequestParam: URL 쿼리 파라미터(cno, page)를 메소드 매개변수로 받습니다.
     */
    @GetMapping
    public PageDto getPostList(@RequestParam int cno, @RequestParam int page) {
        // 서비스 계층을 호출하여 페이징 처리된 게시물 목록을 가져옵니다.
        return postService.getPostList(cno, page);
    }

    /**
     * [3] 게시물 삭제 컨트롤러
     * @param pno 삭제할 게시물 번호
     * @return 성공 여부 (boolean)
     *
     * - @DeleteMapping: HTTP DELETE 요청을 처리합니다.
     */
    @DeleteMapping
    public boolean deletePost(@RequestParam int pno) {
        // 서비스 계층을 호출하여 게시물을 삭제합니다.
        return postService.deletePost(pno);
    }

    /**
     * [4] 게시물 수정 컨트롤러
     * @param postDto { pno, ptitle, pcontent } JSON 객체
     * @return 성공 여부 (boolean)
     *
     * - @PutMapping: HTTP PUT 요청을 처리합니다.
     */
    @PutMapping
    public boolean updatePost(@RequestBody PostDto postDto) {
        // 서비스 계층을 호출하여 게시물을 수정합니다.
        return postService.updatePost(postDto);
    }

    /**
     * [3] 게시물 개별 조회 (조회수 중복 방지 로직 추가)
     * @param pno 조회할 게시물 번호
     * @param session 현재 세션
     * @return 게시물 상세 정보
     */
    @GetMapping("/view")
    public PostDto getPost(@RequestParam int pno, HttpSession session) {
        // 1. 세션에서 'viewHistory' 속성을 가져옴
        Object attribute = session.getAttribute("viewHistory");
        Map<Integer, String> viewHistory;

        if (attribute == null) {
            // 2. 세션에 기록이 없으면 새로운 HashMap 생성
            viewHistory = new HashMap<>();
        } else {
            // 3. 기록이 있으면 기존 Map을 가져옴
            viewHistory = (Map<Integer, String>) attribute;
        }

        // 4. 오늘 날짜를 문자열로 가져옴
        String today = LocalDate.now().toString();
        // 5. 현재 게시물(pno)을 오늘(today) 본 기록이 있는지 확인
        String lastViewDate = viewHistory.get(pno);

        if (lastViewDate == null || !lastViewDate.equals(today)) {
            // 6. 오늘 처음 보는 게시물이면
            // - 조회수 증가 서비스를 호출
            postService.incrementViewCount(pno);
            // - 세션에 오늘 날짜로 조회 기록을 저장/업데이트
            viewHistory.put(pno, today);
            session.setAttribute("viewHistory", viewHistory);
        }

        // 1. 세션에서 현재 로그인된 회원 번호 가져오기
        Object loginMnoObj = session.getAttribute("loginMno");
        int loginMno = (loginMnoObj != null) ? (Integer)loginMnoObj : 0;

        // 2. 서비스에 게시물 번호와 로그인 회원 번호를 전달
        return postService.getPost(pno, loginMno);

    }

    @PostMapping("/reply")
    public boolean writeReply(@RequestBody Map<String, String> map , HttpSession session) {
        // 1. 세션에서 로그인된 회원 번호 가져오기
        Object loginMnoObject = session.getAttribute("loginMno");
        if (loginMnoObject == null) {
            return false; // 비로그인 시 실패
        }
        int loginMno = (Integer) loginMnoObject;

        // 2. DTO에 회원 번호 설정
        map.put( "mno" , loginMno+"" );

        // 3. 서비스 호출
        return postService.writeReply(map);
    }

    @GetMapping("/reply")
    public List<Map<String, String>> getReplies(@RequestParam int pno) {
        return postService.getReplies(pno);
    }



}
