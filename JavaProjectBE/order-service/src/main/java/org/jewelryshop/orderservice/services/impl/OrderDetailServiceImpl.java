package org.jewelryshop.orderservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;
import org.jewelryshop.orderservice.dto.response.OrderDetailResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.jewelryshop.orderservice.mapper.OrderDetailMapper;
import org.jewelryshop.orderservice.repositories.OrderDetailRepository;
import org.jewelryshop.orderservice.repositories.OrderRepository;
import org.jewelryshop.orderservice.services.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderRepository orderRepository;
    @Override
    public OrderDetailResponse create(OrderDetailRequest orderDetailRequest) {
        Order order = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow();
        OrderDetail orderDetail= orderDetailMapper.toOrderDetail(orderDetailRequest);
        orderDetail.setOrder(order);
        orderDetailRepository.save(orderDetail);
        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }

    @Override
    public void update(String orderDetailId, OrderDetailRequest orderDetailRequest) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
        orderDetailMapper.updateOrderDetail(orderDetail,orderDetailRequest);
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetailResponse getByOrderDetailId(String orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }

    @Override
    public void delete(String orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public List<OrderDetailResponse> getAll() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream().map(orderDetailMapper::toOrderDetailResponse).collect(Collectors.toList());
        return orderDetailResponses;
    }
}
