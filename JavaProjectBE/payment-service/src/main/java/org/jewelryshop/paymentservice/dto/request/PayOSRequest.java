package org.jewelryshop.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PayOSRequest {
    private int orderCode;
    private Integer amount;
    private String description;
    private String cancelUrl;
    private String returnUrl;
    private long expiredAt;
    private String signature;
}
