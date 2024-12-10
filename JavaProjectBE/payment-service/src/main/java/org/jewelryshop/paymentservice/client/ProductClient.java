package org.jewelryshop.paymentservice.client;

import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
@Component
@FeignClient(name = "product-service", url = "http://localhost:9001")
public interface ProductClient {
    @PutMapping("/product/reduce-stock/{id}")
    ApiResponse<Boolean> reduceProductStock(@PathVariable String id);

}
