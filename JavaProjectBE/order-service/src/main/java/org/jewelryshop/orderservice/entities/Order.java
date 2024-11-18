package org.jewelryshop.orderservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private String status;

    @Column(name = "total_amount")
    private double totalAmount;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(insertable=false, updatable=false)
    private List<OrderDetail> orderDetails;

}
