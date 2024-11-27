package org.jewelryshop.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private String paymentStatus;
    private int orderCode;
    private LocalDateTime createdAt;
    private String checkoutUrl;
}

