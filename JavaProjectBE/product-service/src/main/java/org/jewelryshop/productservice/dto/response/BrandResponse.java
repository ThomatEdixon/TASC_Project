package org.jewelryshop.productservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private String brandId;
    private String name;
    private String description;
}
