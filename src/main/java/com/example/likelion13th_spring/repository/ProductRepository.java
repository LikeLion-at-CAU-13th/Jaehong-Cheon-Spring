package com.example.likelion13th_spring.repository;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    // 판매자 기준으로 상품 찾기
    List<Product> findBySeller(Member seller);
}
