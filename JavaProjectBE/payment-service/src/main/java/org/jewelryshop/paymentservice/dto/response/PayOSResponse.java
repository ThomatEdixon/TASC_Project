package org.jewelryshop.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayOSResponse {
    private String code;
    private String desc;
    private DataPayOSResponse data;
    private String signature;

}
