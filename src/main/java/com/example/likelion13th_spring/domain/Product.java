package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity { //BaseTimeEntity를 상속하면서 각 엔티티별 작성시간, 수정시간 기록
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 상품이름
    @Column(nullable = false)
    private Integer price; // 상품가격
    @Column(nullable = false)
    private Integer stock; //상품재고
    @Column(nullable = false)
    private String description; // 상품 정보

    @ManyToOne
    @JoinColumn(name = "seller_id") // 연관관계의 주인!!
    private Member seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 영속성 전이
    private List<ProductOrders> productOrders;

    public void reduceStock(int amount) {
        this.stock -= amount;
    }
}