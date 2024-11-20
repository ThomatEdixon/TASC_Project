package org.jewelryshop.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentLinkRequest {
    private String productId;
    private double price;
    private String orderId;
    private String description;
    private String returnUrl;
    private String cancelUrl;
}

