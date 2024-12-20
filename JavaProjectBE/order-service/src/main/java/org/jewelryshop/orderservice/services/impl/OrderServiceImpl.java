package org.jewelryshop.orderservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.orderservice.client.PaymentClient;
import org.jewelryshop.orderservice.client.ProductClient;
import org.jewelryshop.orderservice.client.UserClient;
import org.jewelryshop.orderservice.constants.OrderStatus;
import org.jewelryshop.orderservice.dto.request.*;
import org.jewelryshop.orderservice.dto.response.*;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.jewelryshop.orderservice.mapper.OrderDetailMapper;
import org.jewelryshop.orderservice.mapper.OrderMapper;
import org.jewelryshop.orderservice.repositories.OrderRepository;
import org.jewelryshop.orderservice.services.OrderDetailService;
import org.jewelryshop.orderservice.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserClient userClient;

    private final ProductClient productClient;

    private final PaymentClient paymentClient;

    private final OrderMapper orderMapper;

    private final OrderDetailMapper orderDetailMapper;

    private final OrderDetailService orderDetailService;

    private final OrderProducerService orderProducerService;
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest){

        ApiResponse<UserResponse> userResponse = userClient.getUserById(orderRequest.getUserId());
        List<OrderDetail> orderDetails = new ArrayList<>();
        //  Get totalAmount
        int totalAmount = 0;
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            ApiResponse<ProductResponse> product;
            product = productClient.getProductById(orderDetailRequest.getProductId());
            System.out.println(product.toString());
            orderDetailRequest.setPricePerUnit(orderDetailRequest.getPricePerUnit());
            orderDetailRequest.setTotalPrice(orderDetailRequest.getQuantity() * orderDetailRequest.getPricePerUnit());
            totalAmount += orderDetailRequest.getTotalPrice();
            orderDetails.add(orderDetailMapper.toOrderDetail(orderDetailRequest));
        }

        // Create Order
        Order order = orderMapper.toOrder(orderRequest);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDetails(orderDetails);
        order = orderRepository.save(order);

        // save orderDetail
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            orderDetailRequest.setOrderId(order.getOrderId());
            orderDetailService.create(orderDetailRequest);
        }
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        // send producer to topic kafka

        return orderResponse;

    }

    @Override
    public void updateOrderStatus(String orderId , StatusResponse response) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(response.getStatus());

        // send producer to topic kafka
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        orderProducerService.sendOrderEvent("order-topic", orderResponse);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return orderMapper.toOrderResponse(order);
    }
    @Override
    public void confirmPaymentMethod(String orderId, String paymentMethod) {
        Order order = orderRepository.findByOrderId(orderId);
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        orderResponse.setPaymentMethod(paymentMethod);

    }

    @Override
    public Page<OrderResponse> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<OrderResponse> orderResponses = orders.map(orderMapper::toOrderResponse);

        return orderResponses;
    }

}
