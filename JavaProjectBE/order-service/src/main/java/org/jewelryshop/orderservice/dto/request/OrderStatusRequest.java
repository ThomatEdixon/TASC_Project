package org.jewelryshop.orderservice.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {
    private String orderId;
    private List<OrderStatusDetailRequest> items;
}
