package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.Material;
import org.jewelryshop.productservice.entities.ProductImage;

import java.util.List;

public interface ProductImageDAO {
    void save(ProductImageRequest productImageReques);
    List<ProductImage> getProductImagesByProductId(String productId);
    void update(ProductImage productImage);
    void delete(String productImage);
}
