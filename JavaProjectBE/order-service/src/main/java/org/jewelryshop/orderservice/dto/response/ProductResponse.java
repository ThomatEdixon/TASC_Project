package org.jewelryshop.orderservice.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String categoryName;
    private String brandName;
    private String status;
}
