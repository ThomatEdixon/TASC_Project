package org.jewelryshop.productservice.mappers;

import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.entities.Brand;

import java.util.UUID;

public class BrandMapper {
    public static Brand toBrand(BrandRequest brandRequest){
        return Brand.builder()
                .brandId(UUID.randomUUID().toString())
                .name(brandRequest.getName())
                .description(brandRequest.getDescription())
                .build();
    }
    public static BrandResponse toBrandResponse(Brand brand){
        return BrandResponse.builder()
                .name(brand.getName())
                .description(brand.getDescription())
                .build();
    }
}
