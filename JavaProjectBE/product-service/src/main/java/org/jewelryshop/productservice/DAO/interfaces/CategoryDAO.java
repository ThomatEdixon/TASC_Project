package org.jewelryshop.productservice.DAO.interfaces;

import org.jewelryshop.productservice.entities.Category;

import java.util.List;

public interface CategoryDAO {
    void save(Category category);
    List<Category> getAll();
    Category getById(String categoryId);
    void update(String categoryId, Category category);
    void delete(String categoryId);
}
