package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.services.impl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    @PostMapping
    public ApiResponse<CategoryResponse> save(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createBrand(categoryRequest))
                .build();
    }
    @GetMapping()
    public ApiResponse<List<CategoryResponse>> getById() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getAll())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable String id) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable String id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateBrand(id,categoryRequest);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
