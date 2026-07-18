package com.projoker.smart_bazar.service.cart;

import com.projoker.smart_bazar.Repository.CartItemRepository;
import com.projoker.smart_bazar.Repository.CartRepository;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Cart;
import com.projoker.smart_bazar.model.CartItem;
import com.projoker.smart_bazar.model.Product;
import com.projoker.smart_bazar.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. get cart
        //2. get product
        //3. Check the product is already exist in cart
        //4. If yes, increase the quantity with requested quantity
        //5. Else initiate a new cart item

        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId()==null){
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removalItemFromCart(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        CartItem cartItem=getCartItem(cartId,productId);
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart=cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        //Change the amount on cart too
        BigDecimal totalAmount=cart.getItems()
                .stream().map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart=cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }
}
