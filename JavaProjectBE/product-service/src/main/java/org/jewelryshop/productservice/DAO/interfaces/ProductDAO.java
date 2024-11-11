package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ProductDAO {
    void save(Product product);
    Page<Product> getAll(int page , int size);
    Long getTotalProduct();
    Product findById(String productId);
    void update(String product_id , Product product);
    void delete(String productId);
    void addProductMaterial(String productId, String name);
    List<ProductResponse> searchProducts(int page , int size,String name, Double minPrice, Double maxPrice,
                                         String materialName, String categoryName , String brandName);
}
