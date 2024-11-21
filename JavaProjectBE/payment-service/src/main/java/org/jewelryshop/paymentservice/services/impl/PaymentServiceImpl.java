package org.jewelryshop.paymentservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.client.OrderClient;
import org.jewelryshop.paymentservice.client.ProductClient;
import org.jewelryshop.paymentservice.contants.PaymentMethod;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PayOSRequest;
import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.request.TransactionRequest;
import org.jewelryshop.paymentservice.dto.response.*;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.exceptions.AppException;
import org.jewelryshop.paymentservice.exceptions.ErrorCode;
import org.jewelryshop.paymentservice.mappers.PaymentMapper;
import org.jewelryshop.paymentservice.repositories.PaymentRepository;
import org.jewelryshop.paymentservice.services.PayOSService;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.jewelryshop.paymentservice.services.TransactionService;
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

    private final TransactionService transactionService;
    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest)  {
        ApiResponse<OrderResponse> order;

        order = orderClient.getOrderById(paymentRequest.getOrderId());
        System.out.println(order);

        if (!PaymentStatus.PENDING.equals(order.getData().getStatus())) {
            throw new AppException(ErrorCode.ORDER_NOT_PENDING);
        }

        Payment payment = paymentMapper.toPayment(paymentRequest);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setOrderCode(Integer.parseInt(generateOrderCode()));
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        // Trả về thông tin thanh toán
        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(String paymentId)  {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public void updatePaymentStatus(int orderCode, StatusResponse statusResponse) {
        Payment payment = paymentRepository.findByOrderCode(orderCode);
        payment.setPaymentStatus(statusResponse.getStatus());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Override
    public PaymentResponse getUrlPaymentMethod(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        PaymentResponse paymentResponse = paymentMapper.toPaymentResponse(payment);
        ApiResponse<OrderResponse> order = orderClient.getOrderById(payment.getOrderId());
        if(payment.getPaymentStatus().equals(PaymentStatus.PENDING)){
            switch (payment.getPaymentMethod()){
                case PaymentMethod.PayOS:
                    PayOSRequest payOSRequest = PayOSRequest.builder()
                            .amount(Integer.valueOf(String.valueOf(order.getData().getTotalAmount())))
                            .returnUrl("http://localhost:9003/payment/payment-success")
                            .cancelUrl("https://your-cancel-url.com")
                            .description(generateDescription())
                            .orderCode(payment.getOrderCode())
                            .expiredAt(0)
                            .signature("")
                            .build();

                    try {
                        String urlPayment = payOSService.createPaymentRequest(payOSRequest);
                        ObjectMapper objectMapper = new ObjectMapper();
                        PayOSResponse response = objectMapper.readValue(urlPayment, PayOSResponse.class);
                        paymentResponse.setCheckoutUrl(response.getData().getCheckoutUrl());
                        System.out.printf(response.toString());
                        TransactionRequest transactionRequest = TransactionRequest.builder()
                                .transactionId(response.getData().getPaymentLinkId())
                                .paymentMethod(payment.getPaymentMethod())
                                .transactionCode(response.getData().getQrCode())
                                .transactionStatus(response.getData().getStatus())
                                .amount(response.getData().getAmount())
                                .paymentId(payment.getPaymentId())
                                .build();
                        transactionService.createTransaction(transactionRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PaymentMethod.PayPal:
                    break;
                case PaymentMethod.VNPay:
                    break;
            }
        }
        return paymentResponse;
    }

    @Override
    public StatusResponse reduceStock(int orderCode) {
        StatusResponse statusResponse = new StatusResponse();
        Payment payment = paymentRepository.findByOrderCode(orderCode);
        ApiResponse<OrderResponse> order = orderClient.getOrderById(payment.getOrderId());
        ProductStockRequest productStockRequest = new ProductStockRequest();
        productStockRequest.setOrderDetailResponses(order.getData().getOrderDetails());
        boolean reduceStock = productClient.reduceProductStock(productStockRequest).getData();
        if(reduceStock){
            statusResponse.setStatus(PaymentStatus.SUCCESS);
        }else {
            statusResponse.setStatus(PaymentStatus.REFUND);
        }
        updatePaymentStatus(payment.getOrderCode(),statusResponse);
        return statusResponse;
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
