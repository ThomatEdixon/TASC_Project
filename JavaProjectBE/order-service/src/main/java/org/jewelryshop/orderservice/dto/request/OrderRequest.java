package org.jewelryshop.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String userId;

    private LocalDateTime orderDate;

    private String status;

    private List<OrderDetailRequest> orderDetails;
}
