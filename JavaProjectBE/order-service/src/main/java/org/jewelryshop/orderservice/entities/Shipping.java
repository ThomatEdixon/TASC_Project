package org.jewelryshop.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable=false, updatable=false)
    private String shippingId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    private String status;

    private String address;
}
