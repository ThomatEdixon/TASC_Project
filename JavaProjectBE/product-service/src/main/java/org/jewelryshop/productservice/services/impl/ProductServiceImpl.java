package org.jewelryshop.productservice.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jewelryshop.productservice.DAO.impl.ProductDAOImpl;
import org.jewelryshop.productservice.client.OrderClient;
import org.jewelryshop.productservice.client.PaymentClient;
import org.jewelryshop.productservice.dto.request.ProductRequest;
import org.jewelryshop.productservice.dto.request.ProductStockRequest;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.dto.response.OrderResponse;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.mappers.ProductMapper;
import org.jewelryshop.productservice.services.ProductService;
import org.jewelryshop.productservice.services.RedisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAOImpl productDAO;
    private final ProductMapper productMapper;
    private final PaymentClient paymentClient;
    private final OrderClient orderClient;
    private final RedisService redisService;

    public ProductServiceImpl(ProductDAOImpl productDAO, ProductMapper productMapper, PaymentClient paymentClient, OrderClient orderClient, RedisService redisService) {
        this.productDAO = productDAO;
        this.productMapper = productMapper;
        this.paymentClient = paymentClient;
        this.orderClient = orderClient;
        this.redisService = redisService;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        productDAO.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size) {
        String cacheKey = "products_page:"+ page +"_size"+size;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Kiểm tra cache
        if(redisService.checkExistsKey(cacheKey)){
            List<Product> cachedProducts = redisService.getValues(cacheKey, new TypeReference<List<Product>>() {});
            if (cachedProducts != null && !cachedProducts.isEmpty()) {
                List<ProductResponse> productResponses = cachedProducts.stream()
                        .map(productMapper::toProductResponse)
                        .collect(Collectors.toList());

                Pageable pageable = PageRequest.of(page, size);
                return new PageImpl<>(productResponses, pageable, cachedProducts.size());
            }
        }

        // Nếu không có trong cache, lấy từ database
        Page<Product> products = productDAO.getAll(page, size);

        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());

        // Lưu vào Redis
        redisService.setValue(cacheKey, products.getContent());

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }




    @Override
    public ProductResponse getById(String productId) {
        String cacheKey = "product_" + productId;

        // Kiểm tra cache
        Product cachedProduct = redisService.getValues(cacheKey, new TypeReference<Product>() {});
        if (cachedProduct != null) {
            // Nếu có cache thì trả về kết quả từ Redis
            return productMapper.toProductResponse(cachedProduct);
        }

        // Nếu không có trong cache, lấy từ database
        Product product = productDAO.findById(productId);
        ProductResponse productResponse = productMapper.toProductResponse(product);

        // Lưu vào Redis cache
        redisService.setValue(cacheKey, productResponse);

        return productResponse;
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

    @Override
    public void addProductMaterial(String productId, String materialName) {
        productDAO.addProductMaterial(productId,materialName);
    }

    @Override
    public boolean checkStock(ProductStockRequest stockRequest) {
        return productDAO.checkStock(stockRequest);
    }

    @Override
    public boolean reduceStock(String orderId) {
        ApiResponse<OrderResponse> orderResponse = orderClient.getOrderById(orderId);
        ProductStockRequest stockRequest = ProductStockRequest.builder().orderDetailResponses(orderResponse.getData().getOrderDetails()).build();
        if(checkStock(stockRequest)){
            return productDAO.reduceStock(stockRequest);
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public List<String> getRelatedProductByBrandId(String productId) {
        return productDAO.findProductRelatedByBrandId(productId);
    }
}
