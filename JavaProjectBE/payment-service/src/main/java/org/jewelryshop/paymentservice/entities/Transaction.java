package org.jewelryshop.paymentservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String transactionId;

    private String paymentId;

    private String transactionCode;

    private Double amount;

    private String transactionStatus;

    private String paymentMethod;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
