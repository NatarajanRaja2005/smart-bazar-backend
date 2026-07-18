package com.projoker.smart_bazar.Repository;

import com.projoker.smart_bazar.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
