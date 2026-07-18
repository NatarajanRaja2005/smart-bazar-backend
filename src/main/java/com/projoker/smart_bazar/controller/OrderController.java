package com.projoker.smart_bazar.controller;

import com.projoker.smart_bazar.dto.OrderDto;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Order;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.service.order.IOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name="Order",
        description = "Api to place order and get order details"
)
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order= (Order) orderService.placeOrder(userId);
            OrderDto orderDto=orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order created Success!",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occured",e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order= orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order created Success!",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops!",e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> order= orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order created Success!",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops!",e.getMessage()));
        }
    }
}
