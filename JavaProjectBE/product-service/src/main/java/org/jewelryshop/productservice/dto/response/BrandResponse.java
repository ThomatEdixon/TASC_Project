package org.jewelryshop.productservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private String name;
    private String description;
}
