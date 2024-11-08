package org.jewelryshop.productservice.mappers;

import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class ProductImageMapper {
    public ProductImage toProductImage(ProductImageRequest productImageRequest){
        return ProductImage.builder()
                .imageId(UUID.randomUUID().toString())
                .productId(productImageRequest.getProductId())
                .imageUrl(productImageRequest.getImageUrl())
                .build();
    }
}
