package org.jewelryshop.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    private String orderId;
    private String productId;
    private int quantity;
    private int pricePerUnit;
    private int totalPrice;
}
