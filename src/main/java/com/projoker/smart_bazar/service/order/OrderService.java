package com.projoker.smart_bazar.service.order;

import com.projoker.smart_bazar.Repository.OrderRepository;
import com.projoker.smart_bazar.Repository.ProductRepository;
import com.projoker.smart_bazar.dto.OrderDto;
import com.projoker.smart_bazar.enums.OrderStatus;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.*;
import com.projoker.smart_bazar.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);

        Order order=createOrder(cart);
        List<OrderItem> orderItemList=createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);

        //Now after saved order clear the cart
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order=new Order();
        //Set the user
        User user=cart.getUser();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        //user.getOrder().add(order);
        return order;
    }
    private List<OrderItem> createOrderItems(Order order,Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product=cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);

            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items){
        return items.stream()
                        .map(item -> item.getPrice()
                                .multiply(new BigDecimal(item.getQuantity())))
                        .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders=orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
