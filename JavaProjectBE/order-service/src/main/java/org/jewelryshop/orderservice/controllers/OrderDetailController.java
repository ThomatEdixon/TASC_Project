package org.jewelryshop.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.jewelryshop.orderservice.dto.response.OrderDetailResponse;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.services.OrderDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-detail")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderById(@PathVariable String orderId){
        return ApiResponse.<OrderDetailResponse>builder()
                .data(orderDetailService.getByOrderDetailId(orderId))
                .build();
    }
}
