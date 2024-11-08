package org.jewelryshop.productservice.entities;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Material {
    private String name;
    private String description;
    private Set<Product> products;
}
