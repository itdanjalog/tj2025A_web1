package web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.model.dto.ProductDto;
import web.service.FileService;
import web.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private FileService fileService;

    // [1] 제품 등록
    @PostMapping("/create")
    public int createProduct( @ModelAttribute ProductDto productDto , HttpSession session) {

        if( session.getAttribute("loginMno") == null ) return 0 ;

        int mno = (int)session.getAttribute("loginMno");
        productDto.setMno( mno );

        int result = productService.createProduct(productDto); // 제품 저장
        if (result > 0 && !productDto.getUploads().isEmpty() && !productDto.getUploads().get(0).isEmpty() ) {
            // 제품 이미지 업로드 & DB 저장
            for( MultipartFile multipartFile : productDto.getUploads() ){
                String filename =  fileService.fileUpload( multipartFile );
                productService.saveProductImages( result, filename );
            }
        }
        return result; // 1 = 성공, 0 = 실패
    }

    // [2] 모든 제품 조회
    @GetMapping("/list")
    public List<ProductDto> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return products;
    }

}