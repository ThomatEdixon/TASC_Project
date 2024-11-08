package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.BrandDAO;
import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.entities.Brand;
import org.jewelryshop.productservice.mappers.BrandMapper;
import org.jewelryshop.productservice.services.interfaces.IBrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {
    private final BrandDAO brandDAO;

    public BrandService(BrandDAO brandDAO) {
        this.brandDAO = brandDAO;
    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = BrandMapper.toBrand(brandRequest);
        brandDAO.save(brand);
        return BrandMapper.toBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> getAll() {
        List<Brand> brands = brandDAO.getAll();
        return brands.stream().map(BrandMapper::toBrandResponse).collect(Collectors.toList());
    }

    @Override
    public BrandResponse getById(String id) {
        Brand brand = brandDAO.getById(id);
        return BrandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(String brandId, BrandRequest brandRequest) {
        Brand brand = BrandMapper.toBrand(brandRequest);
        brandDAO.update(brandId,brand);
        return BrandMapper.toBrandResponse(brand);
    }

    @Override
    public void delete(String id) {
        brandDAO.delete(id);
    }
}
