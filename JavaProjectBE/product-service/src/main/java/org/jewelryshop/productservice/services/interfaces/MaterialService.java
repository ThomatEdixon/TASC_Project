package org.jewelryshop.productservice.services.interfaces;

import org.jewelryshop.productservice.entities.Material;

import java.util.List;

public interface MaterialService {
    Material create(Material material);
    Material getByName(String name);
    Material update (String name, Material material);
    void delete(String name);
    List<Material> getAll();
}
