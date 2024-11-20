package org.jewelryshop.paymentservice.mappers;

import org.jewelryshop.paymentservice.dto.request.PaymentRequest;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.jewelryshop.paymentservice.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "createdAt" ,ignore = true)
    @Mapping(target = "updatedAt" ,ignore = true)
    Payment toPayment(PaymentRequest paymentRequest);
    PaymentResponse toPaymentResponse(Payment payment);
}
