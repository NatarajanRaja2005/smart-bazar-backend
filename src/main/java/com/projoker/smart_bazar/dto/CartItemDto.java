package com.projoker.smart_bazar.dto;

import com.projoker.smart_bazar.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemid;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;

}
