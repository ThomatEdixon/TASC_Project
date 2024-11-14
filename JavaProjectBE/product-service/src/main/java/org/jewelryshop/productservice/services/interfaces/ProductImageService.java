package org.jewelryshop.productservice.services.interfaces;

import org.jewelryshop.productservice.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    List<ProductImage> uploadImages(String productId, MultipartFile[] files) throws IOException;
    void update(String imageId, MultipartFile[] files) throws IOException;
    ProductImage getById(String imageId);
    void delete(String imageId);
}
