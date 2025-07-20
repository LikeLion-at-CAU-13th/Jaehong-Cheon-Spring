package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role; // 판매자면 SELLER, 구매자면 BUYER

    private Boolean isAdmin; // 관리자 계정인지

    private Integer deposit; // 현재 계좌 잔액

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL) // 영속성 전이 멤버를 영속화할 때 구매한 물건들도 같이 영속화
    private Set<Product> products = new HashSet<>(); // 연관관계 주인 아님!!

    // 계좌 잔액 충전하기
    public void chargeDeposit(int money){
        this.deposit += money;
    }

    //계좌 잔액 사용하기
    public void useDeposit(int money){
        this.deposit -= money;
    }


}