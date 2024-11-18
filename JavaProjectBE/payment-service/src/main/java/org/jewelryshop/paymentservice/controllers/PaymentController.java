package org.jewelryshop.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.exceptions.AppException;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) throws AppException {
        return ApiResponse.<PaymentResponse>builder()
                .data(paymentService.createPayment(paymentRequest))
                .build();
    }

    @PutMapping("/status/{paymentId}")
    public ApiResponse<Void> updatePaymentStatus(
            @PathVariable String paymentId,
            @RequestParam StatusResponse statusResponse) throws AppException {
        paymentService.updatePaymentStatus(paymentId, statusResponse);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable String paymentId) throws AppException {
        return ApiResponse.<PaymentResponse>builder()
                .data(paymentService.getPaymentById(paymentId))
                .build();
    }
}

