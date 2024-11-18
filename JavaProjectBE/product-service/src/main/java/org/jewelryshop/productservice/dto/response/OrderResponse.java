package org.jewelryshop.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String userId;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private List<OrderDetailResponse> orderDetails;
}
