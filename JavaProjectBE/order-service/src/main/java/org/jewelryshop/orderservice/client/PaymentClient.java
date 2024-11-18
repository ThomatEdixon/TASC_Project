package org.jewelryshop.orderservice.client;

import org.jewelryshop.orderservice.dto.request.PaymentRequest;
import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.jewelryshop.orderservice.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "payment-service",url = "http://localhost:9003")
public interface PaymentClient {
    @PostMapping("/payment")
    ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest);
}
