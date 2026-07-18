package com.projoker.smart_bazar.controller;


import com.projoker.smart_bazar.dto.UserDto;
import com.projoker.smart_bazar.exception.AlreadyExistsException;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.User;
import com.projoker.smart_bazar.request.CreateUserRequest;
import com.projoker.smart_bazar.request.UserUpdateRequest;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name="Users",
        description = "Api to add user and can perform the operations of update delete and create user"
)
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user=userService.getUserById(userId);
            UserDto userDto=userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user=userService.createUser(request);
            UserDto userDto=userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created Successfully",userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,
                                                  @PathVariable Long userId){
        try {
            User user=userService.updateUser(request, userId);
            UserDto userDto=userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!",null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
