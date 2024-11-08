package org.jewelryshop.productservice.services.interfaces;

import org.jewelryshop.productservice.entities.ProductImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ProductImageService {
    List<ProductImage> uploadImages(String productId, MultipartFile[] files) throws IOException;
    Resource loadImageResponse(String imageName) throws MalformedURLException;
}
