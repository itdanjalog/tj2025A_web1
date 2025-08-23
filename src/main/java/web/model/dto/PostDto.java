package web.model.dto;

import lombok.*;

@Getter@Setter@ToString
// 롬복 이용한 getter and setter / ToString 메소드 자동 생성
@NoArgsConstructor@AllArgsConstructor // 기본생성자 and 전체매개변수 생성자를 자동 생성
@Builder
public class PostDto {
    private int pno;            // 게시물번호
    private String ptitle;		// 게시물제목
    private String pcontent;	// 게시물내용
    private int pview;			// 게시물조회수
    private String pdate;		// 게시물작성일
    private int mno; 			// 작성자의 회원번호
    private int cno;			// 카테고리의 번호
    // + HTML에 출력할때 작성자의 회원번호가 아닌 작성자 ID 출력
    private String mid;
    // + HTML에 출력할때 카테고리의 번호가 아닌 카테고리명을 출력
    private String cname;

    private boolean host; // 현재 로그인된 회원이 작성자인지 여부

} // class end