package org.jewelryshop.orderservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelryshop.orderservice.constants.OrderStatus;
import org.jewelryshop.orderservice.dto.response.PaymentResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.repositories.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumerService {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "product-topic", groupId = "jewelry-shop-group")
    public void listenPaymentEvent(String message) {
        log.info(message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PaymentResponse paymentResponse = mapper.readValue(message, PaymentResponse.class);
            Order order = orderRepository.findByOrderId(paymentResponse.getOrderId());
            if (order != null) {
                  if(paymentResponse.getPaymentStatus().equals(OrderStatus.ERROR)){
                      order.setStatus(OrderStatus.ERROR);
                  }else {
                      order.setStatus(paymentResponse.getPaymentStatus());
                  }
                orderRepository.save(order);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process order event", e);
        }
    }
}
