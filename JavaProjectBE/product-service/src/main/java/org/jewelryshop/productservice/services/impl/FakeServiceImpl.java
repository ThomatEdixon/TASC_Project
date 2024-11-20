package org.jewelryshop.productservice.services.impl;

import com.github.javafaker.Faker;
import org.jewelryshop.productservice.DAO.impl.ProductDAOImpl;
import org.jewelryshop.productservice.entities.Product;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FakeServiceImpl {
    private final ProductDAOImpl productDAO;
    private Faker faker = new Faker();
    private Random random = new Random();

    public FakeServiceImpl(ProductDAOImpl productDAO) {
        this.productDAO = productDAO;
    }

    public void initFakeData() throws SQLException {
        List<String> brandIds = productDAO.getAllBrandIds();
        List<String> categoryIds = productDAO.getAllCategoryIds();

        // Danh sách các từ khóa liên quan đến trang sức
        List<String> jewelryKeywords = Arrays.asList("Ring", "Necklace", "Bracelet", "Earrings", "Pendant", "Charm", "Bangle", "Cuff", "Gemstone", "Diamond");

        for (int i = 0; i < 200; i++) {
            Product product = new Product();
            product.setProductId(UUID.randomUUID().toString());

            // Tạo tên sản phẩm liên quan đến trang sức
            String jewelryName = jewelryKeywords.get(random.nextInt(jewelryKeywords.size())) + " " + faker.commerce().material();
            product.setName(jewelryName);

            product.setDescription(faker.lorem().sentence(10));
            product.setPrice(Integer.parseInt(faker.commerce().price()));
            product.setOriginalPrice((int) (product.getPrice() * 1.1));  // Giá gốc cao hơn giá bán 10%
            product.setStockQuantity(faker.number().numberBetween(1, 100));

            // Chọn ngẫu nhiên brand_id và category_id
            product.setBrandId(brandIds.get(random.nextInt(brandIds.size())));
            product.setCategoryId(categoryIds.get(random.nextInt(categoryIds.size())));

            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            productDAO.save(product);
        }
    }
    public void initFakeDataProductMaterial() throws SQLException{
        List<String> productIds = productDAO.getAllProductIds();
        List<String> materialNames = productDAO.getAllMaterialNames();
        for (int i = 0; i < 400; i++) {
            String productId = productIds.get(random.nextInt(productIds.size()));
            String materialName = materialNames.get(random.nextInt(materialNames.size()));
            if(productDAO.getCountProductIdInProductMaterial(productId) >=2){
                i--;
            }else{
                productDAO.addProductMaterial(productId,materialName);
            }
        }
    }
}
