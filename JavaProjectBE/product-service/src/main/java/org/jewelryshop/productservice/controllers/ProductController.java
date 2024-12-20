package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.request.ProductStockRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.services.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/{id}")
    public ApiResponse<Void> addMaterial(@PathVariable String id , @RequestParam String materialName){
        productService.addProductMaterial(id,materialName);
        return ApiResponse.<Void>builder()
                .build();
    }
    @GetMapping("/public/getAll")
    public ApiResponse<Page<ProductResponse>> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .data(productService.getAll(page,size))
                .build();
    }
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable String productId) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getById(productId))
                .build();
    }
    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> searchProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String brandName) {

        return ApiResponse.<Page<ProductResponse>>builder()
                .data(productService.searchProducts(page,size,name,minPrice,maxPrice,materialName,categoryName,brandName))
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
    @PostMapping("/check-stock")
    public ApiResponse<Boolean> checkProductStock(@RequestBody ProductStockRequest stockRequest) {
        return ApiResponse.<Boolean>builder().data(productService.checkStock(stockRequest)).build();
    }

    @PutMapping("/reduce-stock/{id}")
    public ApiResponse<Boolean> reduceProductStock(@PathVariable String id) {
        return ApiResponse.<Boolean>builder().data(productService.reduceStock(id)).build();
    }
}
