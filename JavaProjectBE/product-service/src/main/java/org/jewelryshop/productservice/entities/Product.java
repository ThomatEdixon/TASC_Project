package org.jewelryshop.productservice.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private double originalPrice;
    private int stockQuantity;
    private String categoryId;
    private String brandId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductImage> productImages;
    private Set<Material> materials;
}
