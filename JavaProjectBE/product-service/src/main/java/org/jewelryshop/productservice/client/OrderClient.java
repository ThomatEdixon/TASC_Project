package org.jewelryshop.productservice.client;

import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.OrderResponse;
import org.jewelryshop.productservice.dto.response.StatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "order-service" , url = "http://localhost:9002")
public interface OrderClient {
    @GetMapping("/order/{orderId}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable("orderId") String orderId);
    @PostMapping("/order/update-status/{orderId}")
    void updateOrderStatus(@PathVariable String orderId, @RequestBody StatusResponse statusResponse);
}
