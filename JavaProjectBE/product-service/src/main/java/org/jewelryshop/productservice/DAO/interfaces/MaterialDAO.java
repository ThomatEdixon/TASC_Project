package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.entities.Category;
import org.jewelryshop.productservice.entities.Material;

import java.util.List;
import java.util.Set;

public interface MaterialDAO {
    void save(Material material);
    List<Material> getAll();
    Material getMaterialByName(String materialName);
    void update(String materialName, Material category);
    void delete(String materialName);
    Set<Material> getMaterialByProductId(String productId);
}
