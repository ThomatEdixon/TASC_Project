package org.jewelryshop.productservice.DAO;

import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.entities.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {

    private final DataSource dataSource;

    private final ProductImageDAO productImageDAO;

    public ProductDAO(DataSource dataSource, ProductImageDAO productImageDAO) {
        this.dataSource = dataSource;
        this.productImageDAO = productImageDAO;
    }

    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO product (product_id, name, description, price, original_price, stock_quantity, category_id, brand_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setDouble(5, product.getOriginalPrice());
            stmt.setInt(6, product.getStockQuantity());
            stmt.setString(7, product.getCategoryId());
            stmt.setString(8, product.getBrandId());
            stmt.setTimestamp(9, Timestamp.valueOf(product.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(product.getUpdatedAt()));

            stmt.executeUpdate();
        }
    }

    public Page<Product> getAll(int page , int size) {
        List<Product> products = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        int pageSize = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageSize;
        String sql = "SELECT * FROM product LIMIT " + pageSize + " OFFSET " + offset;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try(ResultSet resultSet = preparedStatement.executeQuery(sql)){
                while (resultSet.next()) {
                    Product product = Product.builder()
                            .productId(resultSet.getString("product_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .price(resultSet.getDouble("price"))
                            .originalPrice(resultSet.getDouble("original_price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryId(resultSet.getString("category_id"))
                            .brandId(resultSet.getString("brand_id"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .build();
                    products.add(product);
                    product.setProductImages(productImageDAO.getProductImagesByProductId(resultSet.getString("product_id")));
                }
            }
            // Câu truy vấn đếm tổng số sản phẩm
            return new PageImpl<>(products, pageable, getTotalProduct());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching products", e);
        }
    }
    public Long getTotalProduct(){
        String sql = "SELECT COUNT(*) FROM product";
        Long total = 0L;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try( ResultSet resultSet = preparedStatement.executeQuery(sql)) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }

    public Product findById(String productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        Product product = new Product();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, productId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    product = Product.builder()
                            .productId(resultSet.getString("product_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .price(resultSet.getDouble("price"))
                            .originalPrice(resultSet.getDouble("original_price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryId(resultSet.getString("category_id"))
                            .brandId(resultSet.getString("brand_id"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .build();

                    product.setProductImages(productImageDAO.getProductImagesByProductId(productId));
                }
            }
        }
        return product;
    }

    public void update(String product_id , Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, original_price = ?, stock_quantity = ?, category_id = ?, brand_id = ?, updated_at = ? " +
                "WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setDouble(4, product.getOriginalPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getCategoryId());
            stmt.setString(7, product.getBrandId());
            stmt.setTimestamp(8, Timestamp.valueOf(product.getUpdatedAt()));
            stmt.setString(9, product_id);

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(String productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, productId);
            stmt.executeUpdate();
        }
    }
    public void addProductMaterial(String productId, String name)  {
        String query = "INSERT INTO Product_Material (product_id, material_name) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public int getCountProductIdInProductMaterial(String productId){
        String sql = "SELECT COUNT(product_material.product_id) FROM product_material GROUP BY product_id HAVING product_id = "+productId;
        int count =0;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            try( ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    count +=1;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }
    public List<String> getAllBrandIds() throws SQLException {
        List<String> brandIds = new ArrayList<>();
        String query = "SELECT brand_id FROM Brand";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                brandIds.add(rs.getString("brand_id"));
            }
        }
        return brandIds;
    }

    public List<String> getAllCategoryIds() throws SQLException {
        List<String> categoryIds = new ArrayList<>();
        String query = "SELECT category_id FROM Category";
        try (   Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categoryIds.add(rs.getString("category_id"));
            }
        }
        return categoryIds;
    }
    public List<String> getAllProductIds() {
        List<String> productIds = new ArrayList<>();
        String sql = "SELECT product_id FROM product";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                productIds.add(resultSet.getString("product_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productIds;
    }
    public List<String> getAllMaterialNames() {
        List<String> materialNames = new ArrayList<>();
        String sql = "SELECT name FROM material";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                materialNames.add(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialNames;
    }


}

