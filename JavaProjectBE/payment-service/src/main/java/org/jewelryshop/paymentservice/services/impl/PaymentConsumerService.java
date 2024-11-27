package org.jewelryshop.paymentservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.response.PaymentResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumerService {

    private final ObjectMapper objectMapper;
    private final PaymentProducerService producerService;
    @KafkaListener(topics = "product-topic", groupId = "jewelry-shop-group")
    public void listenOrderEvent(String message) {
        log.info(message);
        try {
            objectMapper.registerModule(new JavaTimeModule());
            PaymentResponse paymentResponse = objectMapper.readValue(message,PaymentResponse.class);
            if(paymentResponse.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
                producerService.sendPaymentEvent("order-topic", paymentResponse);
            }else {
                producerService.sendPaymentEvent("order-topic", paymentResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process payment event", e);
        }
    }
}
