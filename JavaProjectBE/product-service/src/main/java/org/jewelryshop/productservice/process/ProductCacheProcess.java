package org.jewelryshop.productservice.process;

import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.services.RedisService;

import java.util.concurrent.BlockingQueue;

public class ProductCacheProcess implements Runnable {
    private final RedisService redisService;
    private final BlockingQueue<Product> productQueue; // Queue nội bộ từ ProductCacheManager

    public ProductCacheProcess(RedisService redisCacheService, BlockingQueue<Product> productQueue) {
        this.redisService = redisCacheService;
        this.productQueue = productQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Lấy sản phẩm từ hàng đợi
                Product product = productQueue.poll();
                if (product != null) {
                    // Lưu sản phẩm vào Redis
                    redisService.setValue("product_" + product.getProductId(), product);
                } else {
                    // Nếu không có sản phẩm trong hàng đợi, dừng lại trong 100ms trước khi tiếp tục
                    Thread.sleep(100);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
