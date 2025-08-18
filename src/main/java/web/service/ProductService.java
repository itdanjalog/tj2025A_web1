package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.ProductDao;
import web.model.dto.ProductDto;

import java.util.List;

@Service // 스프링 컨테이너에 서비스 빈 등록
public class ProductService {

    @Autowired
    private ProductDao productDao;

    // [1] 제품 등록
    public int createProduct(ProductDto productDto) {
        // DB에 제품 저장
        return productDao.createProduct(productDto);
    }

    // [2] 제품 이미지 저장 (DB에 파일명 기록)
    public boolean saveProductImages(int pno, String filename) {
        return productDao.saveProductImages( pno , filename);
    }

    // [3] 모든 제품 조회
    public List<ProductDto> getAllProducts() {
        List<ProductDto> result =  productDao.getAllProducts();

        for( ProductDto productDto : result ) {
            List<String> result2 = productDao.getProductImages( productDto.getPno() );
            if( result2.isEmpty() ) continue;
            productDto.setImages( result2 );
        }
        return  result;
    }

    public ProductDto getProduct( int pno ) {
        ProductDto products = productDao.getProduct( pno );
        List<String> result2 = productDao.getProductImages( products.getPno() );
        products.setImages( result2 );
        return products;
    }

}