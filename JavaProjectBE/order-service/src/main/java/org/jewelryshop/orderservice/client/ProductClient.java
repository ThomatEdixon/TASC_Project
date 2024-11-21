package org.jewelryshop.orderservice.client;

import org.jewelryshop.orderservice.dto.request.ProductStockRequest;
import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.jewelryshop.orderservice.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "product-service", url = "http://localhost:9001")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET,value = "/product/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("productId") String productId);

    @RequestMapping(method = RequestMethod.PUT,value = "/product/reduce-stock")
    ApiResponse<Boolean> reduceProductStock(@RequestBody ProductStockRequest stockRequest);
}
