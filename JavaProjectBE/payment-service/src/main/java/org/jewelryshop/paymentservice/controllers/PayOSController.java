package org.jewelryshop.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.dto.request.PayOSRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.jewelryshop.paymentservice.services.PayOSService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payOS")
@RequiredArgsConstructor
public class PayOSController {
    private final PayOSService payOSService;
    @PostMapping
    private ApiResponse<String> createPayOs(PayOSRequest payOSRequest){
        return ApiResponse.<String>builder()
                .data(payOSService.createPaymentRequest(payOSRequest))
                .build();
    }
    @GetMapping("/payment-success")
    public String paymentSuccess(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam Long orderCode) {

        System.out.println("Code: " + code);
        System.out.println("ID: " + id);
        System.out.println("Cancel: " + cancel);
        System.out.println("Status: " + status);
        System.out.println("Order Code: " + orderCode);

        return "Thanh toán thành công!";
    }
}
