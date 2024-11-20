package org.jewelryshop.orderservice.mapper;

import org.jewelryshop.orderservice.dto.request.OrderDetailRequest;
import org.jewelryshop.orderservice.dto.response.OrderDetailResponse;
import org.jewelryshop.orderservice.entities.Order;
import org.jewelryshop.orderservice.entities.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "order",ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest orderDetailRequest);
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
    void updateOrderDetail(@MappingTarget OrderDetail orderDetail , OrderDetailRequest orderDetailRequest);
}
