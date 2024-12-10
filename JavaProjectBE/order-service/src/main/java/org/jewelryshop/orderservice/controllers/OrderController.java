package org.jewelryshop.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.orderservice.dto.request.OrderRequest;
import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.dto.response.StatusResponse;

import org.jewelryshop.orderservice.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.createOrder(orderRequest))
                .build();
    }
    @GetMapping
    public ApiResponse<Page<OrderResponse>> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<OrderResponse> ordersPage = orderService.getAll(pageNumber, pageSize);

        // Trả về phản hồi dạng ApiResponse
        return ApiResponse.<Page<OrderResponse>>builder()
                .data(ordersPage)
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable String orderId){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.getOrderById(orderId))
                .build();
    }
    @GetMapping("/payment-method/{orderId}")
    public ApiResponse<Void> updateStatusByPaymentMethod(@PathVariable String orderId ,@RequestParam String paymentMethod ){
        orderService.confirmPaymentMethod(orderId,paymentMethod);
        return ApiResponse.<Void>builder()
                .build();
    }
    @PostMapping("/update-status/{orderId}")
    ApiResponse<Void> updateOrderStatus(@PathVariable String orderId, @RequestBody StatusResponse statusResponse){
        orderService.updateOrderStatus(orderId,statusResponse);
        return ApiResponse.<Void>builder()
                .build();
    }

}
