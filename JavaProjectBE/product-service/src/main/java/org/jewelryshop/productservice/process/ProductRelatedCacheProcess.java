package org.jewelryshop.productservice.process;

import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.services.ProductService;
import org.jewelryshop.productservice.services.RedisService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProductRelatedCacheProcess implements Runnable{
    private RedisService redisCacheService;
    private BlockingQueue<Product> relatedProductQueue;
    private ProductService productService;

    public ProductRelatedCacheProcess(RedisService redisCacheService,BlockingQueue<Product> relatedProductQueue,ProductService productService) {
        this.redisCacheService = redisCacheService;
        this.relatedProductQueue =relatedProductQueue;
        this.productService = productService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!relatedProductQueue.isEmpty()) {
                    Product product = relatedProductQueue.poll();
                    //b1: lay trong queue
                    List<String> relatedProducts = productService.getRelatedProductByBrandId(product.getProductId());
                    redisCacheService.setValue("related_product_"+product.getProductId(), relatedProducts);

                } else {
                    Thread.sleep(100);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
