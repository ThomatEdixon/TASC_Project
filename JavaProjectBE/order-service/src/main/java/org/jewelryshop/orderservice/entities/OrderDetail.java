package org.jewelryshop.orderservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderDetailId;

    @Column(name = "order_id",insertable=false, updatable=false)
    private String orderId;

    @Column(name = "product_id")
    private String productId;

    private int quantity;

    @Column(name = "price_per_unit")
    private double pricePerUnit;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

}
