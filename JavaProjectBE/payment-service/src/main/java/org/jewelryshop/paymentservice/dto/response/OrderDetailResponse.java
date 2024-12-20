package org.jewelryshop.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    String orderId;
    private String productId;
    private int quantity;
    private int pricePerUnit;
    private int totalPrice;
}
