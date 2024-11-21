package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.impl.MaterialDAOImpl;
import org.jewelryshop.productservice.entities.Material;
import org.jewelryshop.productservice.services.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialServiceImpl implements MaterialService {
    private final MaterialDAOImpl materialDAO;

    public MaterialServiceImpl(MaterialDAOImpl materialDAO) {
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
