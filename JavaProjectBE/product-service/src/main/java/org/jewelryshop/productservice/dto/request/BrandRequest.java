package org.jewelryshop.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {

    private String name;
    private String description;
}
