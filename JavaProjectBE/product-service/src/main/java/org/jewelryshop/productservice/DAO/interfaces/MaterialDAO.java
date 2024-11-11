package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.entities.Category;
import org.jewelryshop.productservice.entities.Material;

import java.util.List;

public interface MaterialDAO {
    void save(Material material);
    List<Material> getAll();
    Material getMaterialByName(String materialName);
    void update(String materialName, Material category);
    void delete(String materialName);
}
