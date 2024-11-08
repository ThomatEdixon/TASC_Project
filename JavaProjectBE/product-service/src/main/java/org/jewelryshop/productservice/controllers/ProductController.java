package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.services.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    @PostMapping
    public ApiResponse<ProductResponse> saveProduct(@RequestBody ProductRequest product) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.createProduct(product))
                .build();
    }
    @GetMapping("")
    public ApiResponse<Page<ProductResponse>> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .data(productService.getAll(page,size))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable String id) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id,productRequest);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
