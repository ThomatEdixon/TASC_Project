package org.jewelryshop.productservice.mappers;

import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;

import java.util.UUID;

public class ProductImageMapper {
    public static ProductImage toProductImage(ProductImageRequest productImageRequest){
        return ProductImage.builder()
                .imageId(UUID.randomUUID().toString())
                .productId(productImageRequest.getProductId())
                .imageUrl(productImageRequest.getImageUrl())
                .build();
    }
}
