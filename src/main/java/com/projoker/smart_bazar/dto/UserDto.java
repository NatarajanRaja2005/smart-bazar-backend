package com.projoker.smart_bazar.dto;

import com.projoker.smart_bazar.model.Cart;
import com.projoker.smart_bazar.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> order;
    private CartDto cart;
}
