package org.jewelryshop.productservice.DAO.impl;
import org.jewelryshop.productservice.DAO.interfaces.CategoryDAO;
import org.jewelryshop.productservice.entities.Category;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    private final DataSource dataSource;

    public CategoryDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void save(Category category){
        String sql = "INSERT INTO category(category_id, name , description) VALUES (?,?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){

            preparedStatement.setString(1, category.getCategoryId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Category> getAll(){
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Category category = Category.builder()
                            .categoryId(resultSet.getString("category_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .build();
                    categories.add(category);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }
    @Override
    public Category getById(String categoryId){
        String sql = "SELECT * FROM category WHERE category_id = ?";
        Category category = new Category();
        try(Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setString(1, categoryId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    category.setCategoryId(resultSet.getString("category_id"));
                    category.setName(resultSet.getString("name"));
                    category.setDescription(resultSet.getString("description"));
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return category;
    }
    @Override
    public void update(String categoryId, Category category){
        String sql = "UPDATE category SET category_id = ? , name = ? , description = ? WHERE category_id =?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){

            preparedStatement.setString(1, category.getCategoryId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());
            preparedStatement.setString(4,categoryId);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete(String categoryId){
        String sql = "DELETE FROM category WHERE category_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, categoryId);
            preparedStatement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
}
