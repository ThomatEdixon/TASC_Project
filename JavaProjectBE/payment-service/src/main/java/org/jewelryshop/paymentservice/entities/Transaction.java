package org.jewelryshop.paymentservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @Column(name = "transaction_code")
    private String transactionCode;

    private Double amount;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnore
    private Payment payment;
}
