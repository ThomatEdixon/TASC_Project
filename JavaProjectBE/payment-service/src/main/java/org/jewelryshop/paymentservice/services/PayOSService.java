package org.jewelryshop.paymentservice.services;

import org.jewelryshop.paymentservice.dto.request.PayOSRequest;

public interface PayOSService {
    String createPaymentRequest(PayOSRequest paymentRequest);
}
