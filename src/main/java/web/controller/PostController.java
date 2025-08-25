package web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.PageDto;
import web.model.dto.PostDto;
import web.service.PostService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // (1) HTTP 요청/응답 자료 매핑 기술
@RequestMapping("/post") // (2) HTTP URL 매핑 기술
@RequiredArgsConstructor // (3) final 변수에 대한 자동 생성자 주입
public class PostController {

    private final PostService postService; // @RequiredArgsConstructor 사용함으로 @Autowired 생략 한다.

    // [1] 게시물등록
    @PostMapping("") // method : post , url : localhost:8080/post , body : { "ptitle" : "게시물제목테스트" , "pcontent" : "게시물제목테스트" , "cno" : "1"  }
    public int writePost( @RequestBody PostDto postDto , HttpSession session ){
        // 1. 현재 로그인 상태 확인
        Object login = session.getAttribute("loginMno");
        // 2. 비로그인이면 등록 실패처리
        if( login == null  ) return 0;
        // 3. 로그인이면 현재 로그인한 회원번호를 postDto 대입하기
        int mno = (int)login;
        postDto.setMno( mno );
        // 4. 서비스 호출 하고 응답 반환 하기
        return postService.writePost( postDto );
    } // func end

    /**
     * [2] 게시물 전체 조회 컨트롤러
     * @param cno 카테고리 번호
     * @param page 현재 페이지 번호
     * @return 페이징 처리된 게시물 목록 (PageDto)
     *
     * - @GetMapping: HTTP GET 요청을 처리합니다.
     * - @RequestParam: URL 쿼리 파라미터(cno, page)를 메소드 매개변수로 받습니다.
     */

    // /posts?cno=1&page=1&count=10
    // /posts?cno=1&page=1&count=10&key=title&keyword=스프링
    @GetMapping
    public PageDto findAllPost(@RequestParam(defaultValue = "1") int cno,
                               @RequestParam(defaultValue = "1") int page, // 기본 페이지 1
                               @RequestParam(defaultValue = "5") int count, // 기본 개수 10
                               @RequestParam(required = false) String key, // 검색 키 (필수 아님)
                               @RequestParam(required = false) String keyword) { // 검색어 (필수 아님)

        // 서비스 계층을 호출하여 페이징 및 검색 처리된 게시물 목록을 가져옵니다.
        return postService.findAllPost(cno, page, count, key, keyword);
    }

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




} // class end















