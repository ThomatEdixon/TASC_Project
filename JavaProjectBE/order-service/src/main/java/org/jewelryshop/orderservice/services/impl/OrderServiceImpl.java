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
import org.jewelryshop.orderservice.exceptions.AppException;
import org.jewelryshop.orderservice.exceptions.ErrorCode;
import org.jewelryshop.orderservice.mapper.OrderMapper;
import org.jewelryshop.orderservice.repositories.OrderDetailRepository;
import org.jewelryshop.orderservice.repositories.OrderRepository;
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
    @Override
    @Transactional(rollbackOn = {AppException.class})
    public OrderResponse createOrder(OrderRequest orderRequest) throws AppException {
        try{
            ApiResponse<UserResponse> userResponse = userClient.getUserById(orderRequest.getUserId());

            System.out.printf(userResponse.toString());
        }catch (Exception e){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        //  Validate Products
        double totalAmount = 0.0;
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            ApiResponse<ProductResponse> product;
            try{
               product = productClient.getProductById(orderDetailRequest.getProductId());
               System.out.println(product.toString());
            }catch (Exception e){
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
            orderDetailRequest.setPricePerUnit(product.getData().getPrice());
            orderDetailRequest.setTotalPrice(orderDetailRequest.getQuantity() * product.getData().getPrice());
            totalAmount += orderDetailRequest.getTotalPrice();
        }

        // Create Order
        Order order = orderMapper.toOrder(orderRequest);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // save orderDetail
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductId(orderDetailRequest.getProductId());
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setPricePerUnit(orderDetailRequest.getPricePerUnit());
            orderDetail.setTotalPrice(orderDetailRequest.getTotalPrice());
            orderDetailRepository.save(orderDetail);
        }

        // Process Payment
        PaymentRequest paymentRequest = new PaymentRequest(order.getOrderId(), totalAmount);
        ApiResponse<PaymentResponse> paymentResponse ;
        try{
            paymentResponse = paymentClient.createPayment(paymentRequest);
        }catch (Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }


        // Update Order Status
        if (OrderStatus.SUCCESS.equals(paymentResponse.getData().getPaymentStatus())) {
            order.setStatus(OrderStatus.SUCCESS);
            // Reduce Product Stock
            for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
                ProductStockRequest stockRequest = new ProductStockRequest();
                stockRequest.setProductId(orderDetailRequest.getProductId());
                stockRequest.setQuantity(orderDetailRequest.getQuantity());
                productClient.reduceProductStock(stockRequest);
            }
        } else {
            order.setStatus(OrderStatus.CANCEL);
        }
        orderRepository.save(order);

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
}
