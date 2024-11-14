package org.jewelryshop.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jewelryshop.productservice.entities.Material;
import org.jewelryshop.productservice.entities.ProductImage;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String categoryName;
    private String brandName;
    private List<ProductImage> productImages;
    private Set<Material> materials;
}
