package org.jewelryshop.paymentservice.client;

import org.jewelryshop.paymentservice.dto.request.ProductStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
@Component
@FeignClient(name = "product-service")
public interface ProductClient {
    @PostMapping("/product/check-stock")
    boolean checkProductStock(@RequestBody ProductStockRequest stockRequest);

}
