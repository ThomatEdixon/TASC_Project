package org.jewelryshop.productservice.client;

import org.jewelryshop.productservice.dto.request.PaymentStatusRequest;
import org.jewelryshop.productservice.dto.response.PaymentResponse;
import org.jewelryshop.productservice.dto.response.StatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/payment/{paymentId}")
    PaymentResponse getPaymentById(@PathVariable String paymentId);

    @PostMapping("/payment/update-status")
    StatusResponse updatePaymentStatus(@RequestBody PaymentStatusRequest paymentRequest);
}
