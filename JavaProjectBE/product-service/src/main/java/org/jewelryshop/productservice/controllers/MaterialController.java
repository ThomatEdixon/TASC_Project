package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.entities.Material;
import org.jewelryshop.productservice.services.impl.MaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;
    @PostMapping
    public ApiResponse<Material> saveProduct(@RequestBody Material material) {
        return ApiResponse.<Material>builder()
                .data(materialService.create(material))
                .build();
    }

    @GetMapping("/{name}")
    public ApiResponse<Material> getProductById(@PathVariable String name) {
        return ApiResponse.<Material>builder()
                .data(materialService.getByName(name))
                .build();
    }
    @GetMapping
    public ApiResponse<List<Material>> getAll() {
        return ApiResponse.<List<Material>>builder()
                .data(materialService.getAll())
                .build();
    }
    @PutMapping("/{name}")
    public ApiResponse<Void> updateProduct(@PathVariable String name, @RequestBody Material material) {
        materialService.update(name,material);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteProduct(@PathVariable String name) {
        materialService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}