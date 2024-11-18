package org.jewelryshop.orderservice.dto.response;

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
    private Double pricePerUnit;
    private Double totalPrice;
}
