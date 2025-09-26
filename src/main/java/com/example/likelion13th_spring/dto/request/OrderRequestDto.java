package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    private Long memberId;        // 구매자 ID
    private Long productId;       // 상품 ID
    private Integer quantity;     // 수량
    private String name;          // 수령인
    private String phoneNumber;   // 전화번호
    private String streetAddress; // 도로명주소
//    private String detailAddress; // 상세주소
    private Integer postCode;     // 우편번호

    public Orders toEntity(Member buyer, Product product) {
        ShippingAddress addr = ShippingAddress.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .streetAddress(this.streetAddress)
//                .detailAddress(this.detailAddress)
                .postCode(this.postCode)
                .build();

        return Orders.builder()
                .buyer(buyer)
                .product(product)
                .quantity(this.quantity)
                .deliverStatus(DeliverStatus.PREPARATION)
                .shippingAddress(addr)  // ★ cascade=ALL 로 같이 저장
                .build();
    }
}
