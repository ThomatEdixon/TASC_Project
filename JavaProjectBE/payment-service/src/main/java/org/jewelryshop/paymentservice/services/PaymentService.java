package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.entities.Payment;
import org.jewelryshop.paymentservice.exceptions.AppException;

public interface PaymentService {
    Payment createPayment(PaymentRequest paymentRequest) ;
    PaymentResponse getPaymentById(String paymentId) ;
    void updatePaymentStatus(String paymentId, StatusResponse statusResponse);
    PaymentResponse updateStatusPaymentMethod(PaymentRequest paymentRequest);
}
