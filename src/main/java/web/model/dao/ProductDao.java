package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.ProductDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository // 스프링 컨테이너에 빈 등록
public class ProductDao extends Dao { // Dao는 JDBC Connection 상속

    // [1] 제품 등록
    public int createProduct(ProductDto productDto) {
        try {
            String sql = "INSERT INTO product(pname, pprice, pcomment, plat, plng, mno) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, productDto.getPname());
            ps.setInt(2, productDto.getPprice());
            ps.setString(3, productDto.getPcomment());
            ps.setDouble(4, productDto.getPlat());
            ps.setDouble(5, productDto.getPlng());
            ps.setInt(6, productDto.getMno());

            int count = ps.executeUpdate();

            if (count == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int pno = rs.getInt(1); // 등록된 제품 번호 반환
                    return pno;
                }
            }

        } catch (Exception e) {
            System.out.println("[ProductDao.createProduct] 오류 : " + e);
        }
        return 0;
    }

    // [2] 제품 이미지 저장
    public boolean saveProductImages(int pno, String filename) {
        try {
            String sql = "INSERT INTO productimg(pimgname, pno) VALUES(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, filename);
            ps.setInt(2, pno);
            return ps.executeUpdate()==1; // 성공 1, 실패 0
        } catch (Exception e) {
            System.out.println("[ProductDao.saveProductImages] 오류 : " + e);
        }
        return false;
    }

    // [3] 모든 제품 조회
    public List<ProductDto> getAllProducts() {
        List<ProductDto> list = new ArrayList<>();
        try {
            // 제품 조회
            String sql = "SELECT * FROM product ORDER BY pdate DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDto dto = new ProductDto();
                dto.setPno(rs.getInt("pno"));
                dto.setPname(rs.getString("pname"));
                dto.setPprice(rs.getInt("pprice"));
                dto.setPcomment(rs.getString("pcomment"));
                dto.setPdate(rs.getString("pdate"));
                dto.setPlat(rs.getDouble("plat"));
                dto.setPlng(rs.getDouble("plng"));
                dto.setMno(rs.getInt("mno"));

                list.add(dto);
            }

        } catch (Exception e) {
            System.out.println("[ProductDao.getAllProducts] 오류 : " + e);
        }
        return list;
    }

    // [제품 이미지 조회] Helper
    public List<String> getProductImages(int pno) {
        List<String> images = new ArrayList<>();
        try {
            String sql = "SELECT pimgname FROM productimg WHERE pno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("pimgname"));
            }
        } catch (Exception e) {
            System.out.println("[ProductDao.getProductImages] 오류 : " + e);
        }
        return images;
    }

    public ProductDto getProduct( int pno ) {
        try {
            // 제품 조회
            String sql = "SELECT * FROM product where pno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt( 1 , pno );
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProductDto dto = new ProductDto();
                dto.setPno(rs.getInt("pno"));
                dto.setPname(rs.getString("pname"));
                dto.setPprice(rs.getInt("pprice"));
                dto.setPcomment(rs.getString("pcomment"));
                dto.setPdate(rs.getString("pdate"));
                dto.setPlat(rs.getDouble("plat"));
                dto.setPlng(rs.getDouble("plng"));
                dto.setMno(rs.getInt("mno"));
                return dto;
            }

        } catch (Exception e) {
            System.out.println("[ProductDao.getAllProducts] 오류 : " + e);
        }
        return null;
    }


}