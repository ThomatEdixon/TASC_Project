package org.jewelryshop.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    private String productId;
    private String imageUrl;
}
