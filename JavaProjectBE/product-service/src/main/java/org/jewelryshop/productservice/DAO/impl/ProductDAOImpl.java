package org.jewelryshop.productservice.DAO.impl;

import org.jewelryshop.productservice.DAO.interfaces.ProductDAO;
import org.jewelryshop.productservice.client.OrderClient;
import org.jewelryshop.productservice.contants.ProductStatus;
import org.jewelryshop.productservice.dto.request.ProductStockRequest;
import org.jewelryshop.productservice.dto.response.OrderDetailResponse;
import org.jewelryshop.productservice.dto.response.ProductResponse;
import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.exceptions.AppException;
import org.jewelryshop.productservice.exceptions.ErrorCode;
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
public class ProductDAOImpl implements ProductDAO {

    private final DataSource dataSource;

    private final ProductImageDAOImpl productImageDAO;
    private final MaterialDAOImpl materialDAO;
    private final OrderClient orderClient;

    public ProductDAOImpl(DataSource dataSource, ProductImageDAOImpl productImageDAO, MaterialDAOImpl materialDAO, OrderClient orderClient) {
        this.dataSource = dataSource;
        this.productImageDAO = productImageDAO;
        this.materialDAO = materialDAO;
        this.orderClient = orderClient;
    }
    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product (product_id, name, description, price, original_price, stock_quantity, category_id, brand_id, created_at, updated_at,status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getPrice());
            stmt.setInt(5, product.getOriginalPrice());
            stmt.setInt(6, product.getStockQuantity());
            stmt.setString(7, product.getCategoryId());
            stmt.setString(8, product.getBrandId());
            stmt.setTimestamp(9, Timestamp.valueOf(product.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(product.getUpdatedAt()));
            stmt.setString(11,product.getStatus());

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
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
                            .price(resultSet.getInt("price"))
                            .originalPrice(resultSet.getInt("original_price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryId(resultSet.getString("category_id"))
                            .brandId(resultSet.getString("brand_id"))
                            .status(resultSet.getString("status"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .build();
                    product.setProductImages(productImageDAO.getProductImagesByProductId(resultSet.getString("product_id")));
                    product.setMaterials(materialDAO.getMaterialByProductId(resultSet.getString("product_id")));
                    products.add(product);
                    }
            }
            // Câu truy vấn đếm tổng số sản phẩm
            return new PageImpl<>(products, pageable, getTotalProduct());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching products", e);
        }
    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE 1=1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try(ResultSet resultSet = preparedStatement.executeQuery(sql)){
                while (resultSet.next()) {
                    Product product = Product.builder()
                            .productId(resultSet.getString("product_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .price(resultSet.getInt("price"))
                            .originalPrice(resultSet.getInt("original_price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryId(resultSet.getString("category_id"))
                            .brandId(resultSet.getString("brand_id"))
                            .status(resultSet.getString("status"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .build();
                    product.setProductImages(productImageDAO.getProductImagesByProductId(resultSet.getString("product_id")));
                    product.setMaterials(materialDAO.getMaterialByProductId(resultSet.getString("product_id")));
                    products.add(product);
                }
            }
            // Câu truy vấn đếm tổng số sản phẩm
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching products", e);
        }
    }

    @Override
    public List<String> findProductRelatedByBrandId(String productId) {
        String sql = "SELECT p.product_id FROM products p " +
                "WHERE p.brand_id = (SELECT p2.brand_id FROM products p2 WHERE p2.product_id = ?) " +
                "AND p.product_id <> ? " +
                "LIMIT 5";
        List<String> productIds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId);
            stmt.setString(2, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Thêm productId vào danh sách
                    productIds.add(rs.getString("product_id"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productIds;
    }

    @Override
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

    @Override
    public Product findById(String productId) {
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
                            .price(resultSet.getInt("price"))
                            .originalPrice(resultSet.getInt("original_price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryId(resultSet.getString("category_id"))
                            .status(resultSet.getString("status"))
                            .brandId(resultSet.getString("brand_id"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .build();

                    product.setProductImages(productImageDAO.getProductImagesByProductId(productId));
                    product.setMaterials(materialDAO.getMaterialByProductId(resultSet.getString("product_id")));

                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<ProductResponse> searchProducts(int page , int size,String name, Double minPrice, Double maxPrice, String materialName,
                                                String categoryName, String brandName) {

        List<ProductResponse> products = new ArrayList<>();
        Pageable  pageable = PageRequest.of(page,size);
        int pageSize = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageSize;
        StringBuilder query = new StringBuilder(
                "SELECT p.*, c.name AS categoryName, b.name AS brandName " +
                        "FROM product p " +
                        "JOIN category c ON p.category_id = c.category_id " +
                        "JOIN brand b ON p.brand_id = b.brand_id " +
                        "WHERE 1=1"
        );

        // Thêm điều kiện tìm kiếm cho từng thuộc tính
        if (name != null && !name.isEmpty()) {
            query.append(" AND p.name LIKE ?");
        }
        if (minPrice != null) {
            query.append(" AND p.price >= ?");
        }
        if (maxPrice != null) {
            query.append(" AND p.price <= ?");
        }
        if (materialName != null && !materialName.isEmpty()) {
            query.append(" AND p.product_id IN (SELECT pm.product_id FROM product_material pm JOIN material m ON pm.material_name = m.name WHERE m.name LIKE ?)");
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.append(" AND c.name LIKE ?");
        }
        if (brandName != null && !brandName.isEmpty()) {
            query.append(" AND b.name LIKE ?");
        }
        query.append(" LIMIT "+pageSize+" OFFSET "+offset);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int index = 1;

            if (name != null && !name.isEmpty()) {
                stmt.setString(index++, "%" + name + "%");
            }
            if (minPrice != null) {
                stmt.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                stmt.setDouble(index++, maxPrice);
            }
            if (materialName != null && !materialName.isEmpty()) {
                stmt.setString(index++, "%" + materialName + "%");
            }
            if (categoryName != null && !categoryName.isEmpty()) {
                stmt.setString(index++, "%" + categoryName + "%");
            }
            if (brandName != null && !brandName.isEmpty()) {
                stmt.setString(index++, "%" + brandName + "%");
            }

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    ProductResponse product = ProductResponse.builder()
                            .productId(resultSet.getString("product_id"))
                            .name(resultSet.getString("name"))
                            .description(resultSet.getString("description"))
                            .price(resultSet.getInt("price"))
                            .stockQuantity(resultSet.getInt("stock_quantity"))
                            .categoryName(resultSet.getString("categoryName"))
                            .brandName(resultSet.getString("brandName"))
                            .build();

                    product.setProductImages(productImageDAO.getProductImagesByProductId(resultSet.getString("product_id")));
                    product.setMaterials(materialDAO.getMaterialByProductId(resultSet.getString("product_id")));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public boolean checkStock(ProductStockRequest stockRequest) {
        for (OrderDetailResponse orderDetail : stockRequest.getOrderDetailResponses()){
            String sql = "SELECT stock_quantity FROM product WHERE product_id = ?";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, orderDetail.getProductId());

                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        if(resultSet.getInt("stock_quantity") < orderDetail.getQuantity()){
                            updateProductStatus(ProductStatus.OUT_OF_STOCK);
                            throw new AppException(ErrorCode.OUT_OF_STOCK);
                        }
                        else {
                            return true;
                        }
                    }
                } catch (AppException e) {
                    throw new RuntimeException(e);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean reduceStock(ProductStockRequest stockRequest) {
        for (OrderDetailResponse orderDetail : stockRequest.getOrderDetailResponses()){
            String sql = "UPDATE product SET  stock_quantity = (stock_quantity - ?) " +
                    "WHERE product_id = ?";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1,orderDetail.getQuantity());
                stmt.setString(2, orderDetail.getProductId());
                stmt.executeUpdate();
                updateProductStatus(ProductStatus.SUCCESS);
                return true;
            }catch (SQLException e){
                updateProductStatus(ProductStatus.ERROR);
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void update(String product_id , Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, original_price = ?, stock_quantity = ?, category_id = ?, brand_id = ?, updated_at = ? , status = ?" +
                "WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getPrice());
            stmt.setInt(4, product.getOriginalPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getCategoryId());
            stmt.setString(7, product.getBrandId());
            stmt.setTimestamp(8, Timestamp.valueOf(product.getUpdatedAt()));
            stmt.setString(9, product.getStatus());
            stmt.setString(10, product_id);

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String productId){
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, productId);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
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
    public void updateProductStatus(String productStatus){
        String sql = "UPDATE product SET status = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,productStatus);
            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Fake data

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

