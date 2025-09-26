package com.example.likelion13th_spring.domain.service;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.dto.request.*;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.enums.DeliverStatus;
import com.example.likelion13th_spring.repository.MemberRepository;
import com.example.likelion13th_spring.repository.OrderRepository;
import com.example.likelion13th_spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        // 구매자 조회
        Member buyer = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구매자입니다."));
        //상품 확인
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 구매자 확인
        if (!buyer.isBuyer()) {
            throw new IllegalArgumentException("상품은 구매자만 구매할 수 있습니다.");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        if (product.getStock() < dto.getQuantity()) {
            throw new IllegalArgumentException("구매 가능 수량을 초과했습니다.");
        }

        product.reduceStock(dto.getQuantity());

        // DTO의 toEntity 메서드를 사용하여 배송 주소까지 함께 설정
        Orders order = dto.toEntity(buyer, product);

        Orders saved = orderRepository.save(order);
        return OrderResponseDto.fromEntity(saved);
    }


    // 구매자별 주문 목록 조회
    public List<OrderResponseDto> getOrdersByBuyer(Long id) {
        return orderRepository.findByBuyerId(id).stream()
                .map(OrderResponseDto::fromEntity)
                .toList();
    }

    // 특정 주문 가져오기
    public OrderResponseDto getOrderById(Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
        return OrderResponseDto.fromEntity(order);
    }


    @Transactional
    public OrderResponseDto updateOrderShipping(Long orderId, OrderUpdateDto dto) {
        // 주문 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        // 준비 상태에서만 수정 가능
        if (order.getDeliverStatus() != DeliverStatus.PREPARATION) {
            throw new IllegalArgumentException("배송정보 수정이 불가합니다.");
        }

        // 본인 주문만 수정 가능
        if (!order.getBuyer().getId().equals(dto.getMemberId())) {
            throw new IllegalArgumentException("본인의 주문만 수정할 수 있습니다.");
        }

        order.getShippingAddress().shippingAddressUpdate(
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getStreetAddress(),
//                dto.getDetailAddress(),
                dto.getPostCode()
        );

        return OrderResponseDto.fromEntity(order);
    }
    @Transactional
    public void deleteOrder(Long orderId, OrderDeleteDto dto) {
        // 판매자 조회
        Member buyer = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));

        // 주문 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 권한 확인
        if (!order.getBuyer().getId().equals(dto.getMemberId())) {
            throw new IllegalArgumentException("본인의 주문을 취소할 수 있습니다.");
        }

        // 삭제
        orderRepository.delete(order);
    }
}
