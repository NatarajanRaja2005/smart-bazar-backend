package com.projoker.smart_bazar.service.product;

import com.projoker.smart_bazar.dto.ProductDto;
import com.projoker.smart_bazar.model.Product;
import com.projoker.smart_bazar.request.AddProductRequest;
import com.projoker.smart_bazar.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);

    List<Product> getAllProduct();
    List<Product> getAllProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String category,String name);
    Long countProductByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto converToDto(Product product);
}
