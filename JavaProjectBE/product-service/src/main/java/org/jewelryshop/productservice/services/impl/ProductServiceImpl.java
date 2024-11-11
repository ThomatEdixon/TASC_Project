package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.impl.ProductDAOImpl;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.mappers.ProductMapper;
import org.jewelryshop.productservice.services.interfaces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAOImpl productDAO;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductDAOImpl productDAO, ProductMapper productMapper) {
        this.productDAO = productDAO;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        productDAO.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page , int size) {
        List<Product> products = new ArrayList<>();
        for (Product product : productDAO.getAll(page,size)) {
            products.add(product);
        }
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);

        long totalProducts = productDAO.getTotalProduct();

        return new PageImpl<>(productResponses, pageable, totalProducts);
    }

    @Override
    public ProductResponse getById(String productId) {
        Product product = productDAO.findById(productId);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        productDAO.update(productId,product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public void delete(String productId) {
        productDAO.delete(productId);
    }

    @Override
    public Page<ProductResponse> searchProducts(int page, int size, String name, Double minPrice, Double maxPrice, String materialName, String categoryName, String brandName) {
        Pageable pageable = PageRequest.of(page, size);
        long totalProducts = productDAO.getTotalProduct();
        List<ProductResponse> productResponses = productDAO.searchProducts(page, size,name,minPrice,maxPrice,materialName,categoryName,brandName);
        return new PageImpl<>(productResponses, pageable, totalProducts);
    }
}
