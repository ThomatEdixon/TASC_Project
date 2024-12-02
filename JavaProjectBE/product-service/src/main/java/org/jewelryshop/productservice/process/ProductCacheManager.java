package org.jewelryshop.productservice.process;

import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.services.ProductService;
import org.jewelryshop.productservice.services.RedisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProductCacheManager {

    private final ProductService productService;
    private final RedisService redisCacheService;

    private final BlockingQueue<Product> productQueue; // Queue chính cho sản phẩm
    private final BlockingQueue<Product> relatedProductQueue; // Queue cho sản phẩm liên quan

    private final ExecutorService executorService;
    private final int NUMBER_CONSUMER = 8; // Số thread cho caching

    public ProductCacheManager(ProductService productService, RedisService redisCacheService) {
        this.productService = productService;
        this.redisCacheService = redisCacheService;
        this.productQueue = new LinkedBlockingQueue<>();
        this.relatedProductQueue = new LinkedBlockingQueue<>();
        this.executorService = Executors.newFixedThreadPool(NUMBER_CONSUMER);
    }

    // Nạp sản phẩm từ DB vào queue chính
    public void loadProductsIntoQueue() {
        System.out.println("Loading products into main queue...");

        for (Product product : productService.getAllProducts()) {
            try {
                productQueue.put(product);
                relatedProductQueue.put(product); // Đồng thời nạp vào hàng đợi liên quan
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Loaded products into queues.");
    }

    // Khởi chạy multithreading cho cả hai hàng đợi
    public void startCacheProcesses() {
        // Consumer cho product chính
        for (int i = 0; i < NUMBER_CONSUMER / 2; i++) {
            executorService.submit(new ProductCacheProcess(redisCacheService, productQueue));
        }

        // Consumer cho sản phẩm liên quan
        for (int i = 0; i < NUMBER_CONSUMER / 2; i++) {
            executorService.submit(new ProductRelatedCacheProcess(redisCacheService, productService));
        }

        System.out.println("Started cache processes for products and related products.");
    }

    // Scheduled job để nạp sản phẩm và bắt đầu multithreading
    @Scheduled(cron = "0 0 2 * * ?") // Chạy lúc 2h sáng
    public void scheduledProductCache() {
        loadProductsIntoQueue(); // Load sản phẩm từ database
        startCacheProcesses(); // Bắt đầu caching
    }

    // Scheduled job để kiểm tra và cập nhật sản phẩm đã thay đổi mỗi 15 phút
    @Scheduled(cron = "0 */15 * * * ?")
    public void checkAndUpdateProductCache() {
        System.out.println("Checking and updating product cache...");

        // Lấy tất cả sản phẩm từ database
        List<Product> dbProducts = productService.getAllProducts();

        for (Product dbProduct : dbProducts) {
            String cacheKey = "product_" + dbProduct.getProductId();

            // Lấy sản phẩm từ cache
            Product cachedProduct = (Product) redisCacheService.getValue(cacheKey);

            if (cachedProduct != null) {
                // So sánh updatedAt giữa cache và database
                if (!dbProduct.getUpdatedAt().equals(cachedProduct.getUpdatedAt())) {
                    // Cập nhật cache nếu updatedAt khác nhau
                    redisCacheService.setValue(cacheKey, dbProduct);
                    System.out.println("Updated cache for productId: " + dbProduct.getProductId());
                }
            } else {
                // Thêm sản phẩm mới vào cache nếu chưa tồn tại
                redisCacheService.setValue(cacheKey, dbProduct);
                System.out.println("Added new product to cache with productId: " + dbProduct.getProductId());
            }
        }

        System.out.println("Cache check and update process completed.");
    }

}
