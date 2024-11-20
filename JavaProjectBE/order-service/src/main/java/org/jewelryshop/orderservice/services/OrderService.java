package org.jewelryshop.orderservice.services;

import org.jewelryshop.orderservice.dto.request.OrderRequest;
import org.jewelryshop.orderservice.dto.request.OrderStatusRequest;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.dto.response.StatusResponse;
import org.jewelryshop.orderservice.exceptions.AppException;

public interface OrderService {
    OrderResponse createOrder(OrderRequest order) ;
    void updateOrderStatus(String orderId,StatusResponse response);
    OrderResponse getOrderById(String orderId);
    OrderResponse updateStatusForPayment(OrderRequest orderRequest);
}
