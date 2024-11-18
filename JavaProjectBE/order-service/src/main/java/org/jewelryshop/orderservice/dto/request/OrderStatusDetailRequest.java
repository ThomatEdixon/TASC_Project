package org.jewelryshop.orderservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDetailRequest {
    private String productId;
    private int quantity;
}
