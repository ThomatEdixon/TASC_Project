package org.jewelryshop.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private String checkoutUrl;
}
