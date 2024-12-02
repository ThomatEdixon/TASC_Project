package org.jewelryshop.productservice.process;

import org.jewelryshop.productservice.entities.Product;
import org.jewelryshop.productservice.services.ProductService;
import org.jewelryshop.productservice.services.RedisService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRelatedCacheProcess implements Runnable{
    private RedisService redisCacheService;
    private ProductService productService;
    private final String CACHE_KEY = "product_related_cache_key";

    public ProductRelatedCacheProcess(RedisService redisCacheService,ProductService productService) {
        this.redisCacheService = redisCacheService;
        this.productService =productService;

    }

    @Override
    public void run() {
        try {
            while (true) {
                if (redisCacheService.checkExistsKey(CACHE_KEY)) {
                    //b1: lay trong queue
                    Product product = (Product)redisCacheService.rPop(CACHE_KEY);
                    List<Product> relatedProducts = new ArrayList<>();
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
