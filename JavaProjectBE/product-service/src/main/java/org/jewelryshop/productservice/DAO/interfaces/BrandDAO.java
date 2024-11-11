package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.entities.Brand;

import java.util.List;

public interface BrandDAO {
    void save(Brand brand);
    List<Brand> getAll();
    Brand getById(String brandId);
    void update(String brandId, Brand brand);
    void delete(String brandId);
}
