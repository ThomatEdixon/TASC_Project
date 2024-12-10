package org.jewelryshop.orderservice.services;

import org.jewelryshop.orderservice.dto.request.OrderRequest;
import org.jewelryshop.orderservice.dto.request.OrderStatusRequest;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.dto.response.StatusResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.exceptions.AppException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest order) ;
    void updateOrderStatus(String orderId,StatusResponse response);
    OrderResponse getOrderById(String orderId);
    void confirmPaymentMethod(String orderId, String paymentMethod);
    Page<OrderResponse> getAll(int pageNumber,int pageSize);
}
