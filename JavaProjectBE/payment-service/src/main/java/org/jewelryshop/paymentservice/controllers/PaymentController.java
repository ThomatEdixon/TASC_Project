package org.jewelryshop.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.client.ProductClient;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.jewelryshop.paymentservice.services.TransactionService;
import org.jewelryshop.paymentservice.services.impl.PaymentProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final PaymentProducerService paymentProducerService;

    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return ApiResponse.<PaymentResponse>builder()
                .data(paymentService.createPayment(paymentRequest))
                .build();
    }
    @GetMapping("/url-check-out/{paymentId}")
    public ApiResponse<PaymentResponse> getUrlCheckOut(@PathVariable String paymentId){
        return ApiResponse.<PaymentResponse>builder().data(paymentService.getUrlPaymentMethod(paymentId)).build();
    }
    @PutMapping("/status/{paymentId}")
    public ApiResponse<Void> updatePaymentStatus(
            @PathVariable String paymentId,
            @RequestParam StatusResponse statusResponse)  {
        PaymentResponse payment = paymentService.getPaymentById(paymentId);
        paymentService.updatePaymentStatus(payment.getOrderCode(), statusResponse);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable String paymentId)  {
        return ApiResponse.<PaymentResponse>builder()
                .data(paymentService.getPaymentById(paymentId))
                .build();
    }

    @GetMapping("/payment-success")
    public ApiResponse<Boolean> paymentSuccess(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam Long orderCode) {
        StatusResponse statusResponse = new StatusResponse();
        PaymentResponse paymentResponse = paymentService.getPaymentByOrderCode(Math.toIntExact(orderCode));
        if(!cancel){
            if(status.equals("PAID")){
                statusResponse.setStatus(PaymentStatus.SUCCESS);
                paymentResponse.setPaymentStatus(PaymentStatus.SUCCESS);
                paymentService.updatePaymentStatus(Math.toIntExact(orderCode),statusResponse);
                transactionService.update(id,PaymentStatus.SUCCESS);
                paymentService.reduceStock(Math.toIntExact(orderCode));
                //paymentProducerService.sendPaymentEvent("payment-topic",paymentResponse);
                return ApiResponse.<Boolean>builder().data(true).build();
            }else {
                statusResponse.setStatus(status);
                paymentResponse.setPaymentMethod(status);
                paymentService.updatePaymentStatus(Math.toIntExact(orderCode),statusResponse);
            }
        }else {
            statusResponse.setStatus(PaymentStatus.CANCEL);
            paymentResponse.setPaymentMethod(PaymentStatus.CANCEL);
            paymentService.updatePaymentStatus(Math.toIntExact(orderCode),statusResponse);
        }
        transactionService.update(id,status);
        paymentProducerService.sendPaymentEvent("payment-topic",paymentResponse);
        return ApiResponse.<Boolean>builder().data(false).build();
    }
}

