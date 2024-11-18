package org.jewelryshop.orderservice.repositories;

import org.jewelryshop.orderservice.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
