package com.example.likelion13th_spring.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 수령인
    @Column(nullable = false)
    private String phoneNumber; // 전화번호
    @Column(nullable = false)
    private String streetAddress; //도로명주소
//    @Column(nullable = false)
//    private String detailAddress; // 상세주소
    @Column(nullable = false)
    private Integer postCode; // 우편번호

    public void shippingAddressUpdate(String name, String phoneNumber, String streetAddress, Integer postCode) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
//        this.detailAddress = detailAddress;
        this.postCode = postCode;
    }
}
