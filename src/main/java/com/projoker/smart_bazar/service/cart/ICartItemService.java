package com.projoker.smart_bazar.service.cart;

import com.projoker.smart_bazar.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removalItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
