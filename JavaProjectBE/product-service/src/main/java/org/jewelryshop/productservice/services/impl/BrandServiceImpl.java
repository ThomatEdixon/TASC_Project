package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.BrandDAO;
import org.jewelryshop.productservice.dto.request.BrandRequest;
import org.jewelryshop.productservice.dto.response.BrandResponse;
import org.jewelryshop.productservice.entities.Brand;
import org.jewelryshop.productservice.mappers.BrandMapper;
import org.jewelryshop.productservice.services.interfaces.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandDAO brandDAO;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandDAO brandDAO, BrandMapper brandMapper) {
        this.brandDAO = brandDAO;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = brandMapper.toBrand(brandRequest);
        brandDAO.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> getAll() {
        List<Brand> brands = brandDAO.getAll();
        return brands.stream().map(brandMapper::toBrandResponse).collect(Collectors.toList());
    }

    @Override
    public BrandResponse getById(String id) {
        Brand brand = brandDAO.getById(id);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(String brandId, BrandRequest brandRequest) {
        Brand brand = brandMapper.toBrand(brandRequest);
        brandDAO.update(brandId,brand);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public void delete(String id) {
        brandDAO.delete(id);
    }
}
