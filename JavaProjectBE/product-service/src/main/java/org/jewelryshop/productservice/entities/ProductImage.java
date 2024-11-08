package org.jewelryshop.productservice.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductImage {
    private String imageId;
    private String productId;
    private String imageUrl;
}
