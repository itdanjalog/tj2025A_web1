package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.PostDao;
import web.model.dto.PageDto;
import web.model.dto.PostDto;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor // 롬복제공 : final 변수에 대한 ---(final)생성자 자동-- 제공
public class PostService {
    // (*) @RequiredArgsConstructor 사용시 ( @Autowired 생략해도 자동으로 의존성이 처리된다 )
    private final PostDao postDao;
    // (*) @RequiredArgsConstructor 미사용시
    /*
    private final PostDao postDao;
    @Autowired
    public PostService( PostDao postDao ){
        this.postDao = postDao;
    }
    */

    // [1] 게시물등록
    public int writePost(PostDto postDto ){
        return postDao.writePost( postDto );
    } // func end

    // [2] 게시물 전체조회 *페이징*
    public PageDto findAllPost( int cno , int page , int count , String key , String keyword  ){
        // ******** 1. 페이지별 조회할 시작(인덱스)번호 계산 *************
        int startRow = ( page -1 ) * count;
        // ******** 2/3 번만 검색이 있을때 없을때를 나눠서 totalCount 와 postList 구해보자 ************
        // ******** 2. 자료의 개수 구하기. *************
        int totalCount;
        // ******** 3. 자료의 구하기. *************
        List<PostDto> postList;
        if( key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty() ){ // (1) 검색일떄
            // 만약에 key 와 keyword 가 null 아니면서 .isEmpty() : 비어있으면 true 반환 함수 [ !부정문 ]
            // .(도트/접근)연산자는 변수가 NULL 일때 사용 안된다. ( NullPointerException )
            totalCount = postDao.getTotalCountSearch( cno , key , keyword );
            postList = postDao.findAllSearch( cno , startRow , count , key , keyword );
        }else{  // (2) 검색이 아닐때
            totalCount = postDao.getTotalCount( cno );
            postList = postDao.findAll( cno , startRow , count );
        }
        // ******** 4. 전체 페이지수 구하기  *************
        int totalPage = totalCount % count == 0 ? totalCount/count : totalCount/count + 1 ;
        // ******** 5. 시작버튼 구하기    *************
        int btnCount = 5; // 한 화면에 보여지는 최대 버튼수
        int startBtn = ( ( page - 1 ) / btnCount ) * btnCount + 1;
        // ******** 6. 끝버튼 구하기    *************
        int endBtn = startBtn + btnCount - 1 ;
        if( endBtn > totalPage ) endBtn = totalPage;

        // ************* pageDto 구성하기 ***************
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage( page ); // 현재페이지 번호
        pageDto.setTotalPage( totalPage ); // 전체페이지 수
        pageDto.setPerCount( count ); // 한페이지당 게시물 수
        pageDto.setTotalCount( totalCount ); // 전체 게시물 수
        pageDto.setStartBtn( startBtn ); // 시작 페이징 버튼번호
        pageDto.setEndBtn( endBtn ); // 끝 페이징 버튼번호
        pageDto.setData( postList ); // 페이징한 게시물 리스트
        return pageDto; // 반환
    }

    // [3-1] 게시물 개별 정보 조회
    public PostDto getPost( int pno ){
        return postDao.getPost( pno );
    }

    // [3-2] 게시물 조회수 1증가
    public void incrementView( int pno ){
        postDao.incrementView( pno );
    }

    // [4]
    public boolean deletePost( int pno ){
        return postDao.deletePost( pno );
    }

    // [5]
    public int updatePost( PostDto postDto ){
        return postDao.updatePost( postDto );
    }

    // [6]
    public int writeReply(Map<String,String> reply ){
        return postDao.writeReply( reply );
    }

    // [7]
    public List< Map<String,String> > findAllReply( int pno ){
        return postDao.findAllReply( pno );
    }



} // class end















