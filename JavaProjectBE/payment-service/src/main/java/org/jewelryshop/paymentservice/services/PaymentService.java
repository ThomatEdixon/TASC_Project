package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.exceptions.AppException;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest) throws AppException;
    PaymentResponse getPaymentById(String paymentId) throws AppException;
    void updatePaymentStatus(String paymentId, StatusResponse statusResponse) throws AppException;
}
