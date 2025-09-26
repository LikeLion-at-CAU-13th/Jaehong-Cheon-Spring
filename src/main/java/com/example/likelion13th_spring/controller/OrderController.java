package com.example.likelion13th_spring.controller;

import com.example.likelion13th_spring.domain.service.OrderService;
import com.example.likelion13th_spring.domain.service.ProductService;
import com.example.likelion13th_spring.dto.request.*;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders") // 공통 경로
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrders(@RequestBody OrderRequestDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // 회원 전체 주문 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByBuyer(@PathVariable Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByBuyer(memberId));
    }

    // 특정 주문 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrderShipping(@PathVariable("id") Long id,
                                                            @RequestBody OrderUpdateDto dto) {
        return ResponseEntity.ok(orderService.updateOrderShipping(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id,
                                                @RequestBody OrderDeleteDto dto) {
        orderService.deleteOrder(id, dto);
        return ResponseEntity.ok("주문이 성공적으로 취소되었습니다.");
    }
}
