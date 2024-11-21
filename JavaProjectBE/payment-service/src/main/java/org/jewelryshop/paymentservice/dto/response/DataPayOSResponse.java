package org.jewelryshop.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPayOSResponse {
    private String bin;
    private String accountNumber;
    private String accountName;
    private int amount;
    private String description;
    private long orderCode;
    private String currency;
    private String paymentLinkId;
    private String status;
    private long expiredAt;
    private String checkoutUrl;
    private String qrCode;
}
