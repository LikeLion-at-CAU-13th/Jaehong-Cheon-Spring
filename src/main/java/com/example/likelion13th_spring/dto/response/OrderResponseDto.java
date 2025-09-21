package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private DeliverStatus deliverStatus;
    private String buyerName;
    private String buyerEmail;
    private Long productId;
    private String productName;
    private Integer quantity;

    // 배송지
    private String name;
    private String phoneNumber;
    private String streetAddress;
    private String detailAddress;
    private Integer postCode;

    public static OrderResponseDto fromEntity(Orders o) {
        return OrderResponseDto.builder()
                .id(o.getId())
                .deliverStatus(o.getDeliverStatus())
                .buyerName(o.getBuyer().getName())
                .buyerEmail(o.getBuyer().getEmail())
                .productId(o.getProduct().getId())
                .productName(o.getProduct().getName())
                .quantity(o.getQuantity())
                .name(o.getShippingAddress().getName())
                .phoneNumber(o.getShippingAddress().getPhoneNumber())
                .streetAddress(o.getShippingAddress().getStreetAddress())
//                .detailAddress(o.getShippingAddress().getDetailAddress())
                .postCode(o.getShippingAddress().getPostCode())
                .build();
    }
}
