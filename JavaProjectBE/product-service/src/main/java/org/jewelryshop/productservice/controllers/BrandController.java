package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.services.impl.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    @PostMapping
    public ApiResponse<BrandResponse> saveProduct(@RequestBody BrandRequest brandRequest) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.createBrand(brandRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getProductById(@PathVariable String id) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.getById(id))
                .build();
    }
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAll() {
        return ApiResponse.<List<BrandResponse>>builder()
                .data(brandService.getAll())
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<Void> updateProduct(@PathVariable String id, @RequestBody BrandRequest brandRequest) {
        brandService.updateBrand(id,brandRequest);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable String id) {
        brandService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
