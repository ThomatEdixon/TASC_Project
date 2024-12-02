package org.jewelryshop.productservice.services;

import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.request.ProductStockRequest;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct (ProductRequest productRequest);
    Page<ProductResponse> getAll(int page , int size);
    ProductResponse getById(String productId);
    ProductResponse updateProduct(String productId,ProductRequest productRequest);
    void delete(String productId);
    Page<ProductResponse> searchProducts(int page , int size , String name, Double minPrice, Double maxPrice,
                                         String materialName, String categoryName, String brandName);
    void addProductMaterial(String productId, String name);
    boolean checkStock(ProductStockRequest stockRequest);
    boolean reduceStock(String orderId);
    List<Product> getAllProducts();
}
