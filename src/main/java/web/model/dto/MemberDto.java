package web.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@ToString
public class MemberDto {
    /** 회원 번호 (기본키) */
    private int mno;
    /** 회원 아이디 */
    private String mid;
    /** 비밀번호 */
    private String mpwd;
    /** 회원 이름 */
    private String mname;
    /** 회원 연락처 */
    private String mphone;
    /** 가입일 */
    private String mdate;
}