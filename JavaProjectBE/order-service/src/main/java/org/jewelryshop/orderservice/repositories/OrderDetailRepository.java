package org.jewelryshop.orderservice.repositories;

import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
//    @Query(value = "INSERT INTO order_detail (order_id,product_id,quantity,price_per_unit,total_price)" +
//            "VALUES (:orderDetail.orderId,:orderDetail.productId,:orderDetail.quantity,:orderDetail.pricePerUnit,:orderDetail.totalPrice)", nativeQuery = true)
//    OrderDetail save(OrderDetail orderDetail);
}
