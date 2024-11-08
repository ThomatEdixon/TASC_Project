package org.jewelryshop.productservice.services.interfaces;

import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;

import java.util.List;

public interface IBrandService {
    BrandResponse createBrand(BrandRequest brandRequest);
    List<BrandResponse> getAll();
    BrandResponse getById(String id);
    BrandResponse updateBrand(String brandId,BrandRequest brandRequest);
    void delete(String id);
}
