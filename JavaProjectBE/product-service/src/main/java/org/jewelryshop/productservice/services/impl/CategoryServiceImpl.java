package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.impl.CategoryDAOImpl;
import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.CategoryResponse;
import org.jewelryshop.productservice.entities.Category;
import org.jewelryshop.productservice.mappers.CategoryMapper;
import org.jewelryshop.productservice.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAOImpl categoryDAO;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryDAOImpl categoryDAO, CategoryMapper categoryMapper) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createBrand(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        categoryDAO.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryDAO.getAll();
        return categories.stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(String id) {
        Category category = categoryDAO.getById(id);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateBrand(String categoryId, CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        categoryDAO.update(categoryId,category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void delete(String id) {
        categoryDAO.delete(id);
    }
}
