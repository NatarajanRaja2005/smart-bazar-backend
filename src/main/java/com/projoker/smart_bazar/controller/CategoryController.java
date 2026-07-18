package com.projoker.smart_bazar.controller;


import com.projoker.smart_bazar.exception.AlreadyExistsException;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Category;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.service.category.ICategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name="Category",
        description = "Api to view and implement Operations with Category"
)
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category theCategory=categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success",theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category category=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category theCategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Deleted",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
