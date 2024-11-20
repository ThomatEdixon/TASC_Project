package org.jewelryshop.paymentservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.client.OrderClient;
import org.jewelryshop.paymentservice.client.ProductClient;
import org.jewelryshop.paymentservice.contants.PaymentMethod;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PayOSRequest;
import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.*;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.mappers.PaymentMapper;
import org.jewelryshop.paymentservice.repositories.PaymentRepository;
import org.jewelryshop.paymentservice.services.PayOSService;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderClient orderClient;

    private final ProductClient productClient;

    private final PaymentMapper paymentMapper;

    private final PayOSService payOSService;
    @Override
    @Transactional
    public Payment createPayment(PaymentRequest paymentRequest)  {
        ApiResponse<OrderResponse> order;

        order = orderClient.getOrderById(paymentRequest.getOrderId());
        System.out.println(order);

        if (!PaymentStatus.PENDING.equals(order.getData().getStatus())) {
//            throw new AppException(ErrorCode.ORDER_NOT_PENDING);
        }

        Payment payment = paymentMapper.toPayment(paymentRequest);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setOrderCode(Integer.parseInt(generateOrderCode()));
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        // Trả về thông tin thanh toán
        return payment;
    }

    @Override
    public PaymentResponse getPaymentById(String paymentId)  {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public void updatePaymentStatus(String paymentId, StatusResponse statusResponse) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        payment.setPaymentStatus(statusResponse.getStatus());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Override
    public PaymentResponse updateStatusPaymentMethod(PaymentRequest paymentRequest) {
        Payment payment = createPayment(paymentRequest);
        ApiResponse<OrderResponse> order = orderClient.getOrderById(paymentRequest.getOrderId());
        if(payment.getPaymentStatus().equals(PaymentStatus.PENDING)){
            switch (payment.getPaymentMethod()){
                case PaymentMethod.PayOS:
                    PayOSRequest payOSRequest = PayOSRequest.builder()
                            .amount(Integer.valueOf(String.valueOf(order.getData().getTotalAmount())))
                            .returnUrl("http://localhost:9003/payOS/payment-success")
                            .cancelUrl("https://your-cancel-url.com")
                            .description(generateDescription())
                            .orderCode(payment.getOrderCode())
                            .expiredAt(0)
                            .signature("")
                            .build();
                    String urlPayment = payOSService.createPaymentRequest(payOSRequest);
                    System.out.printf(urlPayment);
                    break;
                case PaymentMethod.PayPal:
                    break;
                case PaymentMethod.VNPay:
                    break;
            }
        }
        return paymentMapper.toPaymentResponse(payment);
    }

    public String generateDescription(){
        String description="";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        description +=sb.toString();
        return description;
    }
    public String generateOrderCode(){
        String description="";
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        description +=sb.toString();
        return description;
    }


}
