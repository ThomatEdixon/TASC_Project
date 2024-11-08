package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.services.impl.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ApiResponse<CategoryResponse> saveProduct(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createBrand(categoryRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getProductById(@PathVariable String id) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProduct(@PathVariable String id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateBrand(id,categoryRequest);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable String id) {
        categoryService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
