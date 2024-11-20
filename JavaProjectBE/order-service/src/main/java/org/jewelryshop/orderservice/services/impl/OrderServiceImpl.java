package org.jewelryshop.orderservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jewelryshop.orderservice.client.PaymentClient;
import org.jewelryshop.orderservice.client.ProductClient;
import org.jewelryshop.orderservice.client.UserClient;
import org.jewelryshop.orderservice.constants.OrderStatus;
import org.jewelryshop.orderservice.constants.PaymentMethod;
import org.jewelryshop.orderservice.dto.request.*;
import org.jewelryshop.orderservice.dto.response.*;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.jewelryshop.orderservice.exceptions.AppException;
import org.jewelryshop.orderservice.exceptions.ErrorCode;
import org.jewelryshop.orderservice.mapper.OrderMapper;
import org.jewelryshop.orderservice.repositories.OrderDetailRepository;
import org.jewelryshop.orderservice.repositories.OrderRepository;
import org.jewelryshop.orderservice.services.OrderDetailService;
import org.jewelryshop.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final UserClient userClient;

    private final ProductClient productClient;

    private final PaymentClient paymentClient;

    private final OrderMapper orderMapper;
    private final OrderDetailService orderDetailService;
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest){

        ApiResponse<UserResponse> userResponse = userClient.getUserById(orderRequest.getUserId());

        System.out.printf(userResponse.toString());
        //  Get totalAmount
        int totalAmount = 0;
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            ApiResponse<ProductResponse> product;
            product = productClient.getProductById(orderDetailRequest.getProductId());
            System.out.println(product.toString());
            orderDetailRequest.setPricePerUnit(orderDetailRequest.getPricePerUnit());
            orderDetailRequest.setTotalPrice(orderDetailRequest.getQuantity() * orderDetailRequest.getPricePerUnit());
            totalAmount += orderDetailRequest.getTotalPrice();
        }

        // Create Order
        Order order = orderMapper.toOrder(orderRequest);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // save orderDetail
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            orderDetailRequest.setOrderId(order.getOrderId());
            orderDetailService.create(orderDetailRequest);
        }


        return orderMapper.toOrderResponse(order);

    }

    @Override
    public void updateOrderStatus(String orderId , StatusResponse response) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(response.getStatus());

        orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return orderMapper.toOrderResponse(order);
    }
    @Override
    public OrderResponse updateStatusForPayment(OrderRequest orderRequest) {
        OrderResponse orderResponse = createOrder(orderRequest);
        // Process Payment
        PaymentRequest paymentRequest = new PaymentRequest(orderResponse.getOrderId(), PaymentMethod.PayOS);
        ApiResponse<PaymentResponse> paymentResponse = paymentClient.createPayment(paymentRequest);
        return orderResponse;
    }
}
