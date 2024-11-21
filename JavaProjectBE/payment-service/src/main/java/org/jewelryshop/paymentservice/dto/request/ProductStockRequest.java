package org.jewelryshop.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jewelryshop.paymentservice.dto.response.OrderDetailResponse;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockRequest {
    private List<OrderDetailResponse> orderDetailResponses;
}

