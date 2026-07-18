package com.projoker.smart_bazar.service.cart;

import com.projoker.smart_bazar.model.Cart;
import com.projoker.smart_bazar.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
