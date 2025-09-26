package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
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
    private Integer age;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isAdmin;

    private Integer deposit;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> sellingProduct = new HashSet<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private Set<Orders> buyingOrders = new HashSet<>();

    public void chargeDeposit(int money){
        this.deposit += money;
    }

    public void useDeposit(int money){
        this.deposit -= money;
    }

    @Builder
    public Member(String name, String address, String email, String phoneNumber,
                  Role role, Boolean isAdmin, Integer deposit, Integer age, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isAdmin = isAdmin;
        this.deposit = deposit;
        this.age = age;
        this.password = password;


    }
    public boolean isSeller() {
        return Role.SELLER.equals(this.role);
    }
    }
}
