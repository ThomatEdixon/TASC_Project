package org.jewelryshop.productservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Brand {
    private String brandId;
    private String name;
    private String description;
    private List<Product> products;

}
