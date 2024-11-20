package org.jewelryshop.paymentservice.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String paymentId;

    private String transactionCode;

    private Double amount;

    private String transactionStatus;

    private String paymentMethod;


}
