package org.jewelryshop.paymentservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id",insertable=false, updatable=false)
    private String paymentId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "order_code")
    private int orderCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "payment" , fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(insertable=false, updatable=false)
    private List<Transaction> transactions;
}

