package com.projoker.smart_bazar.controller;

import com.projoker.smart_bazar.dto.ProductDto;
import com.projoker.smart_bazar.exception.AlreadyExistsException;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Product;
import com.projoker.smart_bazar.request.AddProductRequest;
import com.projoker.smart_bazar.request.UpdateProductRequest;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.service.product.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name="Product",
        description = "Api to view and add products"
)
public class ProductController {
    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products=productService.getAllProduct();
        List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product=productService.getProductById(id);
            ProductDto productDto=productService.converToDto(product);

            return ResponseEntity.ok(new ApiResponse("success",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product savedProduct=productService.addProduct(product);
            return  ResponseEntity.ok(new ApiResponse("Product added Successfully",savedProduct));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request,@PathVariable Long productId){
        try {
            Product theProduct=productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("Update product Success",theProduct));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete Successfully",id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> productList=productService.getProductByBrandAndName(brandName, productName);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            List<ProductDto> convertedProducts=productService.getConvertedProducts(productList);
            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",e.getMessage()));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestBody String category, @RequestParam String brand){
        try {
            List<Product> productList=productService.getProductByCategoryAndBrand(category, brand);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",e.getMessage()));
        }
    }

    @GetMapping("/product/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> product=productService.getProductByName(name);
            if(product.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> product=productService.getProductByBrand(brand);
            if(product.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> product=productService.getAllProductByCategory(category);
            if(product.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try {
            var prodoctCount=productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product Count!",prodoctCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
