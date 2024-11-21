package org.jewelryshop.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PayOSRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.services.PayOSService;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payOS")
@RequiredArgsConstructor
public class PayOSController {
    private final PayOSService payOSService;
    private final PaymentService paymentService;
    @PostMapping
    private ApiResponse<String> createPayOs(PayOSRequest payOSRequest){
        return ApiResponse.<String>builder()
                .data(payOSService.createPaymentRequest(payOSRequest))
                .build();
    }
}
