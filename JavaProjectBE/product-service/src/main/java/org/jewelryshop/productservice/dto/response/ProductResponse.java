package org.jewelryshop.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jewelryshop.productservice.entities.ProductImage;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private double price;
    private int stock_quantity;
    private String categoryName;
    private String brandName;
    private List<ProductImage> productImages;
}
