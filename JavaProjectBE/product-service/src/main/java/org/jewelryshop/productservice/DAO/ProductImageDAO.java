package org.jewelryshop.productservice.DAO;

import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;
import org.jewelryshop.productservice.mappers.ProductImageMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductImageDAO {
    private final DataSource dataSource;

    public ProductImageDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(ProductImageRequest productImageRequest){
        ProductImage productImage = ProductImageMapper.toProductImage(productImageRequest);
        String query = "INSERT INTO product_image (image_id, product_id, image_url) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productImage.getImageId());
            stmt.setString(2, productImage.getProductId());
            stmt.setString(3, productImage.getImageUrl());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<ProductImage> getProductImagesByProductId(String productId){
        List<ProductImage> images = new ArrayList<>();
        String query = "SELECT * FROM product_image WHERE product_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductImage image = new ProductImage();
                    image.setImageId(rs.getString("image_id"));
                    image.setProductId(rs.getString("product_id"));
                    image.setImageUrl(rs.getString("image_url"));
                    images.add(image);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return images;
    }
    public void update(ProductImage productImage) {
        String query = "UPDATE product_image SET product_id = ?, image_url = ? WHERE image_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, productImage.getProductId());
            stmt.setString(2, productImage.getImageUrl());
            stmt.setString(3, productImage.getImageId());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(String imageId) {
        String query = "DELETE FROM product_image WHERE image_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, imageId);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

