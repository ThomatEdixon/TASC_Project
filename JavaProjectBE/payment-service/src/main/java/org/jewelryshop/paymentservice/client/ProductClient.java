package org.jewelryshop.paymentservice.client;

import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.jewelryshop.paymentservice.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
@Component
@FeignClient(name = "product-service", url = "http://localhost:9001")
public interface ProductClient {
    @PostMapping("/product/reduce-stock")
    ApiResponse<Boolean> reduceProductStock(@RequestBody ProductStockRequest stockRequest);

}
