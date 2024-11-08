package org.jewelryshop.productservice.DAO;

import org.jewelryshop.productservice.entities.Category;
import org.jewelryshop.productservice.entities.Material;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MaterialDAO {
    private final DataSource dataSource;

    public MaterialDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Create
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

    // Read
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

    // Update
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

    // Delete
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
}
