package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    private int pno;                  // 제품 코드
    private String pname;             // 제품명
    private int pprice;               // 제품 가격
    private String pcomment;          // 제품 설명
    private String pdate;           // 등록일/시간
    private double plat;              // 위도
    private double plng;              // 경도
    private int mno;                  // 등록자(회원 번호)

    private List<MultipartFile> uploads; // 등록용

    // 연관관계 (1:N) - productimg
    private List<String> images;  // 제품에 등록된 이미지 목록 // 조회용

}
