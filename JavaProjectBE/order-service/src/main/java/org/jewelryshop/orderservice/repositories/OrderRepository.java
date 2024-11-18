package org.jewelryshop.orderservice.repositories;

import org.jewelryshop.orderservice.dto.request.OrderRequest;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,String> {
    @Query(value = "SELECT * FROM `orders` where order_id = :orderId",nativeQuery = true)
    Order findByOrderId(String orderId);
    @Query(value = "INSERT INTO `orders` (order_date, status, total_amount, user_id) VALUES (:orderRequest.orderDate, :orderRequest.status, :orderRequest.totalAmount, :orderRequest.userId)", nativeQuery = true)
    Order save(OrderRequest orderRequest);
}
