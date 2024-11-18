package org.jewelryshop.paymentservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.paymentservice.client.OrderClient;
import org.jewelryshop.paymentservice.client.ProductClient;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.response.*;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.exceptions.AppException;
import org.jewelryshop.paymentservice.exceptions.ErrorCode;
import org.jewelryshop.paymentservice.repositories.PaymentRepository;
import org.jewelryshop.paymentservice.services.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final ProductClient productClient;
    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws AppException {
        ApiResponse<OrderResponse> order;
        try{
            order = orderClient.getOrderById(paymentRequest.getOrderId());
            System.out.println(order);
        }catch (Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (PaymentStatus.PENDING.equals(order.getData().getStatus())) {
            throw new AppException(ErrorCode.ORDER_NOT_PENDING);
        }

        Payment payment = new Payment();
        payment.setOrderId(order.getData().getOrderId());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

//        boolean isOutOfStock = true;
//        for(OrderDetailResponse orderDetailResponse : order.getOrderDetails()){
//            ProductStockRequest productStockRequest = ProductStockRequest.builder()
//                    .productId(orderDetailResponse.getProductId())
//                    .quantity(orderDetailResponse.getQuantity())
//                    .build();
//            isOutOfStock= productClient.checkProductStock(productStockRequest);
//
//        }
//        if(isOutOfStock){
//            updatePaymentStatus(payment.getPaymentId(),StatusResponse.builder().status(PaymentStatus.ERROR).build());
//        }

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }

    @Override
    public PaymentResponse getPaymentById(String paymentId) throws AppException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_PENDING));

        // Chuyển đổi từ Payment entity sang DTO
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }
    @Override
    public void updatePaymentStatus(String paymentId, StatusResponse statusResponse) throws AppException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setPaymentStatus(statusResponse.getStatus());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
