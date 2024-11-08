package org.jewelryshop.productservice.mappers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.DAO.BrandDAO;
import org.jewelryshop.productservice.DAO.CategoryDAO;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class ProductMapper{
    private final CategoryDAO categoryDAO;
    private final BrandDAO brandDAO;

    public Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .originalPrice(productRequest.getOriginalPrice())
                .stockQuantity(productRequest.getStock_quantity())
                .categoryId(productRequest.getCategoryId())
                .brandId(productRequest.getBrandId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    public ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock_quantity(product.getStockQuantity())
                .categoryName(categoryDAO.getById(product.getCategoryId()).getName())
                .brandName(brandDAO.getById(product.getBrandId()).getName())
                .productImages(product.getProductImages())
                .build();
    }
}
