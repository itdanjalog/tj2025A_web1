package web.model.dto;

import lombok.*;

// 출력할 정보(게시물/회원/제품 등) 들과 페이징 정보를 갖는 클래스
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class PageDto {
    // + DB 테이블이 없고 자바에서만 사용되는 DTO
    private int totalCount;     // 1. 전체 자료 개수
    private int currentPage;    // 2. 현재 페이지 번호
    private int totalPage;      // 3. 전체 페이지수
    private int startBtn;       // 4. 페이징버튼 시작번호;
    private int endBtn;         // 5. 페이징버튼 끝번호;
    private int perCount;       // 6. 1페이지당 보이는 자료수
    private Object data;        // 7. 페이징된 자료들.
    // **** Object 사용한 이유 : 정보/자료 호환성 ****
    // 게시물정보 뿐만 아니라 여러 정보들을 저장하기 위해 슈퍼클래스
}













