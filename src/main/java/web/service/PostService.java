package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.model.dao.PostDao;
import web.model.dto.PageDto;
import web.model.dto.PostDto;

import java.util.List;
import java.util.Map;

/**
 * PostService
 * - @Service: 이 클래스가 비즈니스 로직을 처리하는 서비스 계층의 컴포넌트임을 나타냅니다.
 * - @RequiredArgsConstructor: final 필드에 대한 생성자를 자동으로 생성하여 의존성 주입(DI)을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao; // DAO 계층 의존성 주입

    // [1] 게시물 작성 서비스
    public boolean writePost(PostDto postDto) {
        // DAO를 호출하여 데이터베이스에 게시물을 저장합니다.
        return postDao.write(postDto);
    }

    // [2] 게시물 목록 조회 서비스 (페이징 처리)
    public PageDto getPostList(int cno, int page) {
        // --- 페이징 처리 로직 ---
        // 1. 페이지당 표시할 게시물 수
        int display = 5;
        // 2. 페이지당 조회할 시작 인덱스 계산
        int startRow = (page - 1) * display;
        // 3. 카테고리별 전체 게시물 수 조회
        int totalSize = postDao.getTotalSize(cno);
        // 4. 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalSize / display);
        // 5. 페이지 버튼 수
        int btnSize = 5;
        // 6. 시작 버튼 번호 계산
        int startBtn = ((page - 1) / btnSize) * btnSize + 1;
        // 7. 끝 버튼 번호 계산
        int endBtn = startBtn + btnSize - 1;
        if (endBtn > totalPage) {
            endBtn = totalPage;
        }

        // DAO를 호출하여 해당 페이지의 게시물 목록을 가져옵니다.
        List<PostDto> postList = postDao.findAll(cno, startRow, display);

        // 페이징 관련 데이터를 PageDto에 담아 반환합니다.
        return PageDto.builder()
                .page(page)
                .totalCount(totalSize)
                .totalpage(totalPage)
                .startbtn(startBtn)
                .endbtn(endBtn)
                .data(postList)
                .build();
    }

    // [3] 게시물 삭제 서비스
    public boolean deletePost(int pno) {
        return postDao.delete(pno);
    }

    // [4] 게시물 수정 서비스
    public boolean updatePost(PostDto postDto) {
        return postDao.update(postDto);
    }

    // [NEW] 게시물 개별 조회 서비스
    public PostDto getPost(int pno, int loginMno) {
        PostDto postDto = postDao.findByPno(pno);
        // 게시물 정보가 있고, 게시물 작성자(mno)와 로그인한 회원(loginMno)이 같으면
        if (postDto != null && postDto.getMno() == loginMno) {
            postDto.setHost(true); // DTO에 작성자임을 표시
        }
        return postDto;
    }

    // [1] 댓글 작성 서비스
    public boolean writeReply( Map<String, String> map) {
        // DAO를 호출하여 댓글을 DB에 저장
        return postDao.replyWrite(map);
    }

    // [2] 댓글 조회 서비스
    public List<Map<String, String>> getReplies(int pno) {
        // DAO를 호출하여 특정 게시물의 댓글 목록을 가져옴
        return postDao.replyFindAll(pno);
    }

    /**
     * [NEW] 게시물 조회수 증가 서비스
     * - 단순히 DAO를 호출하여 조회수를 1 증가시킵니다.
     * @param pno 게시물 번호
     */
    public void incrementViewCount(int pno) {
        postDao.incrementViewCount(pno);
    }



}