package org.jewelryshop.productservice.DAO.impl;

import org.jewelryshop.productservice.DAO.interfaces.MaterialDAO;
import org.jewelryshop.productservice.entities.Material;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MaterialDAOImpl implements MaterialDAO {
    private final DataSource dataSource;

    public MaterialDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Material material)  {
        String query = "INSERT INTO Material (name, description) VALUES (?, ?)";
        try (   Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, material.getName());
            stmt.setString(2, material.getDescription());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Material> getAll(){
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM material";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Material material = Material.builder()
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .build();
                    materials.add(material);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public Material getMaterialByName(String name)  {
        Material material = null;
        String query = "SELECT * FROM Material WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    material = new Material();
                    material.setName(rs.getString("name"));
                    material.setDescription(rs.getString("description"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return material;
    }

    @Override
    public void update(String name ,Material material)  {
        String query = "UPDATE Material SET name = ?, description = ? WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, material.getName());
            stmt.setString(2, material.getDescription());
            stmt.setString(3, name);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name)  {
        String query = "DELETE FROM Material WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Set<Material> getMaterialByProductId(String productId) {
        Set<Material> materials = new HashSet<>();
        String sql = "SELECT m.* FROM material m JOIN product_material pm " +
                "ON m.name = pm.material_name JOIN product p ON pm.product_id  = p.product_id WHERE pm.product_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setString(1,productId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Material material = Material.builder()
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .build();
                    materials.add(material);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return materials;
    }
}
