package org.jewelryshop.productservice.DAO;

import org.jewelryshop.productservice.entities.Brand;
import org.jewelryshop.productservice.mappers.BrandMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandDAO {
    private final DataSource dataSource;

    public BrandDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void save(Brand brand){
        String sql = "INSERT INTO brand(brand_id, name , description) VALUES (?,?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){

            preparedStatement.setString(1, brand.getBrandId());
            preparedStatement.setString(2, brand.getName());
            preparedStatement.setString(3, brand.getDescription());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Brand> getAll(){
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brand";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Brand brand = Brand.builder()
                            .brandId(resultSet.getString("brand_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .build();
                    brands.add(brand);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return brands;
    }
    public Brand getById(String brandId){
        String sql = "SELECT * FROM brand WHERE brand_id = ?";
        Brand brand = new Brand();
        try(Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setString(1, brandId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    brand.setBrandId(resultSet.getString("brand_id"));
                    brand.setName(resultSet.getString("name"));
                    brand.setDescription(resultSet.getString("description"));
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return brand;
    }
    public void update(String brandId, Brand brand){
        String sql = "UPDATE brand SET brand_id = ? , name = ? , description = ? WHERE brand_id =?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){

            preparedStatement.setString(1, brand.getBrandId());
            preparedStatement.setString(2, brand.getName());
            preparedStatement.setString(3, brand.getDescription());
            preparedStatement.setString(4,brandId);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(String brandId){
        String sql = "DELETE FROM brand WHERE brand_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, brandId);
            preparedStatement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
}
