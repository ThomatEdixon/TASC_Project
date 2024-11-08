package org.jewelryshop.productservice.services.interfaces;


import org.jewelryshop.productservice.dto.request.CategoryRequest;
import org.jewelryshop.productservice.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createBrand(CategoryRequest categoryRequest);
    List<CategoryResponse> getAll();
    CategoryResponse getById(String id);
    CategoryResponse updateBrand(String categoryId,CategoryRequest categoryRequest);
    void delete(String id);
}
