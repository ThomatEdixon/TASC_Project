package org.jewelryshop.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private double originalPrice;
    private int stockQuantity;
    private String categoryId;
    private String brandId;
}
