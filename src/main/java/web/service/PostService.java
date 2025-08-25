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

    // [2] 게시물 전체조회 *페이징*
    public PageDto findAllPost( int cno , int page , int count ,  String key, String keyword) {
        // cno : 카테고리번호 , page : 현재페이지번호 , count : 페이지당 게시물수
        // key : 검색할 필드명(예: title, content) , keyword : 검색어

        // ******** 페이지별 조회할 시작 인덱스 계산 (로직 동일) *************
        int startRow = (page - 1) * count;

        // =================>>> 1. 검색 여부에 따라 분기 처리 ====================
        List<PostDto> postList;
        int totalCount;

        // key와 keyword가 존재하고, 빈 문자열이 아닐 경우 '검색'으로 판단
        if (key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()) {
            // [검색이 있을 경우]
            // 2. DAO에서 검색 조건에 맞는 게시물 총 개수 가져오기
            totalCount = postDao.getTotalCountSearch(cno, key, keyword);
            // 6. DAO에서 검색 조건에 맞는 게시물 리스트 가져오기 (페이징 처리)
            postList = postDao.findAllSearch(cno, startRow, count, key, keyword);
        } else {
            // [검색이 없을 경우] - 기존 로직과 동일
            // 2. DAO에서 카테고리별 게시물 총 개수 가져오기
            totalCount = postDao.getTotalCount(cno);
            // 6. DAO에서 카테고리별 게시물 리스트 가져오기 (페이징 처리)
            postList = postDao.findAll(cno, startRow, count);
        }
        // =================================================================

        // ******** 3. 전체 페이지수 구하기 (로직 동일) *************
        int totalPage = (int) Math.ceil((double) totalCount / count);

        // ******** 4. 시작/끝 버튼 계산 (로직 동일) *************
        int btnCount = 5;
        int startBtn = ((page - 1) / btnCount) * btnCount + 1;
        int endBtn = startBtn + btnCount - 1;
        if (endBtn > totalPage) endBtn = totalPage;

// ******** PageDto 구성하기 (Setter 방식) ***************
        PageDto pageDto = new PageDto(); // 1. 기본 생성자로 객체 생성

// 2. Setter 메소드를 이용해 각 필드에 값 할당
        pageDto.setCurrentPage(page);
        pageDto.setTotalPage(totalPage);
        pageDto.setPerCount(count);
        pageDto.setTotalCount(totalCount);
        pageDto.setStartBtn(startBtn);
        pageDto.setEndBtn(endBtn);
        pageDto.setData(postList);

        return pageDto;
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