package org.jewelryshop.productservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private String categoryId;
    private String name;
    private String description;
}
