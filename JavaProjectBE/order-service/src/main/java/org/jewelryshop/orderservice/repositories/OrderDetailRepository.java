package org.jewelryshop.orderservice.repositories;

import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    OrderDetail findByOrderDetailId(String orderDetailId);
}
