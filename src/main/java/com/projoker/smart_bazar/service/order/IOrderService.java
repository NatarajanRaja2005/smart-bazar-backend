package com.projoker.smart_bazar.service.order;

import com.projoker.smart_bazar.dto.OrderDto;
import com.projoker.smart_bazar.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
