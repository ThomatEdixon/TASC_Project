package org.jewelryshop.productservice.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
    private String categoryId;
    private String name;
    private String description;
    private List<Product> products;
}
