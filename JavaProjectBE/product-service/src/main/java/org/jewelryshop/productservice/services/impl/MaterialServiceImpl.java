package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.MaterialDAO;
import org.jewelryshop.productservice.entities.Material;
import org.jewelryshop.productservice.services.interfaces.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialServiceImpl implements MaterialService {
    private final MaterialDAO materialDAO;

    public MaterialServiceImpl(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    @Override
    public Material create(Material material) {
        materialDAO.save(material);
        return material;
    }

    @Override
    public Material getByName(String name) {
        return materialDAO.getMaterialByName(name);
    }

    @Override
    public Material update(String name, Material material) {
        materialDAO.update(name,material);
        return material;
    }

    @Override
    public void delete(String name) {
        materialDAO.delete(name);
    }

    @Override
    public List<Material> getAll() {
        return materialDAO.getAll();
    }
}
