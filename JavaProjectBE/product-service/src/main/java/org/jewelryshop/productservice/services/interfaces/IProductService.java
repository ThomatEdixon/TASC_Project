package org.jewelryshop.productservice.services.interfaces;

import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct (ProductRequest productRequest);
    Page<ProductResponse> getAll(int page , int size);
    ProductResponse getById(String productId);
    ProductResponse updateProduct(String productId,ProductRequest productRequest);
    void delete(String productId);
}
