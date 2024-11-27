package org.jewelryshop.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String orderId;

    private String userId;

    private LocalDateTime orderDate;

    private String status;

    private int totalAmount;

    private List<OrderDetailResponse> orderDetails;
    private String paymentMethod;
}
