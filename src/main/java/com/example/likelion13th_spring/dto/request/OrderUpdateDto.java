// OrderUpdateDto.java
package com.example.likelion13th_spring.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateDto {
    private Long memberId;         // 수정 요청자(구매자) ID
    private String name;           // 수령인
    private String phoneNumber;    // 전화번호
    private String streetAddress;  // 도로명주소
    private String detailAddress;  // 상세주소
    private Integer postCode;      // 우편번호
}
