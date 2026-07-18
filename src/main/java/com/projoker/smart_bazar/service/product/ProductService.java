package com.projoker.smart_bazar.service.product;


import com.projoker.smart_bazar.Repository.CategoryRepository;
import com.projoker.smart_bazar.Repository.ImageRepository;
import com.projoker.smart_bazar.Repository.ProductRepository;
import com.projoker.smart_bazar.dto.ImageDto;
import com.projoker.smart_bazar.dto.ProductDto;
import com.projoker.smart_bazar.exception.AlreadyExistsException;
import com.projoker.smart_bazar.exception.ProductNotFoundException;
import com.projoker.smart_bazar.model.Category;
import com.projoker.smart_bazar.model.Image;
import com.projoker.smart_bazar.model.Product;
import com.projoker.smart_bazar.request.AddProductRequest;
import com.projoker.smart_bazar.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        //Check if the category is there or not in DB
        //If found, then create new instance for product
        //If no, then save it as new category and then set new product

        if(productExists(request.getName(),request.getBrand())){
            throw new AlreadyExistsException(request.getBrand()+" "+request.getName()+" already exists, You may update the product!");
        }

        Category category= Optional
                .ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    //Creating new category
                            Category category1=new Category(request.getCategory().getName());
                            return categoryRepository.save(category1);
                        }
                );
        //Then setting category
        request.setCategory(category);

        //Here adding products to REPO
        return productRepository.save(createProduct(request,category));
    }

    //Helper method to check weather the product name and with the same brand is already exits or not
    private boolean productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{
                            throw new ProductNotFoundException("Product Not Found!");
                        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExisitingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found Exception!"));
    }

    private Product updateExisitingProduct(Product exitproduct, UpdateProductRequest request){
        exitproduct.setName(request.getName());
        exitproduct.setBrand(request.getBrand());
        exitproduct.setPrice(request.getPrice());
        exitproduct.setInventory(request.getInventory());
        exitproduct.setDescription(request.getDescription());

        Category category=categoryRepository.findByName(request.getCategory().getName());
        exitproduct.setCategory(category);

        return exitproduct;
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::converToDto).toList();
    }

    @Override
    public ProductDto converToDto(Product product){
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        List<Image> images=imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDto=images.stream()
                .map(image->modelMapper.map(image,ImageDto.class))
                .toList();
        productDto.setImages(imageDto);

        return productDto;
    }
}
