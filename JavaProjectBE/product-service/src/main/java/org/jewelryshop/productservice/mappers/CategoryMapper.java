package org.jewelryshop.productservice.mappers;

import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.entities.Brand;
import org.jewelryshop.productservice.entities.Category;

import java.util.UUID;

public class CategoryMapper {
    public static Category toCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
    }
    public static CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
