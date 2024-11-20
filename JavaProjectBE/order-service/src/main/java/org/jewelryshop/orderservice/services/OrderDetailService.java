package org.jewelryshop.orderservice.services;

import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;
import org.jewelryshop.orderservice.dto.response.OrderDetailResponse;
import org.jewelryshop.orderservice.entities.OrderDetail;

import java.util.List;

public interface OrderDetailService{
    OrderDetailResponse create(OrderDetailRequest orderDetailRequest);
    void update(String OrderDetailId,OrderDetailRequest orderDetailRequest);
    OrderDetailResponse getByOrderDetailId(String orderDetailId);
    void delete(String orderDetailId);
    List<OrderDetailResponse> getAll();

}
