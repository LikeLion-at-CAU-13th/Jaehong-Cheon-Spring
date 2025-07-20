package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.enums.DeliverStatus;
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
public class Orders extends BaseTimeEntity {//BaseTimeEntity를 상속하면서 각 엔티티별 작성시간, 수정시간 기록

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus; // 배송상태

    @ManyToOne
    @JoinColumn(name = "buyer_id") // 연관관계의 주인
    private Member buyer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL) // 영속성 전이
    private List<ProductOrders> productOrders;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL) // 영속성 전이
    private Coupon coupon;
}