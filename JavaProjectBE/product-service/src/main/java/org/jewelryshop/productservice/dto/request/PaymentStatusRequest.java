package org.jewelryshop.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusRequest {
    private String paymentId;
    private String orderId;
    private String status;
}
