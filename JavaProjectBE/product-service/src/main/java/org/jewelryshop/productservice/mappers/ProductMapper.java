package org.jewelryshop.productservice.mappers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.DAO.impl.BrandDAOImpl;
import org.jewelryshop.productservice.DAO.impl.CategoryDAOImpl;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class ProductMapper implements RowMapper<Product> {
    private final CategoryDAOImpl categoryDAO;
    private final BrandDAOImpl brandDAO;

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

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
