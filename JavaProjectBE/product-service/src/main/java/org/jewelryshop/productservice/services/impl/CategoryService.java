package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.CategoryDAO;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.entities.Category;
import org.jewelryshop.productservice.mappers.CategoryMapper;
import org.jewelryshop.productservice.services.interfaces.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public CategoryResponse createBrand(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.toCategory(categoryRequest);
        categoryDAO.save(category);
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryDAO.getAll();
        return categories.stream().map(CategoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(String id) {
        Category category = categoryDAO.getById(id);
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateBrand(String categoryId, CategoryRequest categoryRequest) {
        Category category = CategoryMapper.toCategory(categoryRequest);
        categoryDAO.update(categoryId,category);
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public void delete(String id) {
        categoryDAO.delete(id);
    }
}
