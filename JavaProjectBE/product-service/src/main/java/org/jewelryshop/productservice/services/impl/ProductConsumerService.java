package org.jewelryshop.productservice.services.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.productservice.contants.ProductStatus;
import org.jewelryshop.productservice.dto.response.OrderDetailResponse;
import org.jewelryshop.productservice.dto.response.OrderResponse;
import org.jewelryshop.productservice.dto.response.PaymentResponse;
import org.jewelryshop.productservice.dto.response.StatusResponse;
import org.jewelryshop.productservice.services.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductConsumerService {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final ProductProducerService productProducer; // Để gửi sự kiện sau khi trừ kho

    @KafkaListener(topics = "payment-topic", groupId = "jewelry-shop-group")
    public void listenPaymentSuccessEvent(String message) {
        log.info(message);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            PaymentResponse paymentResponse = objectMapper.readValue(message, PaymentResponse.class);
            if (ProductStatus.SUCCESS.equals(paymentResponse.getPaymentStatus())) {
                boolean stockReduced = productService.reduceStock(paymentResponse.getOrderId());

                if (stockReduced) {
                    productProducer.sendStockEvent("product-topic", paymentResponse);
                } else {
                    paymentResponse.setPaymentStatus(ProductStatus.ERROR);
                    productProducer.sendStockEvent("product-topic", paymentResponse);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process stock event", e);
        }
    }

}
