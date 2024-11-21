package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.exceptions.AppException;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest) ;
    PaymentResponse getPaymentById(String paymentId) ;
    void updatePaymentStatus(int orderCode, StatusResponse statusResponse);
    PaymentResponse getUrlPaymentMethod(String paymentId);
    StatusResponse reduceStock(int orderCode);
}
