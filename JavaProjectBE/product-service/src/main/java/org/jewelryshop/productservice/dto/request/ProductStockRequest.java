package org.jewelryshop.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jewelryshop.productservice.dto.response.OrderDetailResponse;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockRequest {
    private List<OrderDetailResponse> orderDetailResponses;
}
