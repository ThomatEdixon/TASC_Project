package org.jewelryshop.productservice.mappers;

import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.entities.Brand;
import org.jewelryshop.productservice.entities.Category;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
    }
    public CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
