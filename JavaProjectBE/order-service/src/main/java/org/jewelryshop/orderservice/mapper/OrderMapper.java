package org.jewelryshop.orderservice.mapper;

import org.jewelryshop.orderservice.dto.request.OrderRequest;
import org.jewelryshop.orderservice.dto.response.OrderResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderDetails" ,ignore = true)
    Order toOrder(OrderRequest orderRequest) ;
    OrderResponse toOrderResponse(Order order);
}
