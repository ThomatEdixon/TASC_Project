package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest) ;
    PaymentResponse getPaymentById(String paymentId) ;
    void updatePaymentStatus(int orderCode, StatusResponse statusResponse);
    PaymentResponse getUrlPaymentMethod(String paymentId);
    StatusResponse reduceStock(int orderCode);
    PaymentResponse getPaymentByOrderCode(int orderCode);
}
