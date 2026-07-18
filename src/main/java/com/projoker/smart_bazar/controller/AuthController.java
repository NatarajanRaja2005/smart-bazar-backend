package com.projoker.smart_bazar.controller;

import com.projoker.smart_bazar.request.LoginRequest;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.response.JwtResponse;
import com.projoker.smart_bazar.security.jwt.JwtUtills;
import com.projoker.smart_bazar.security.user.ShopUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
@Tag(
        name = "Authentication",
        description = "APIs for user authentication and JWT token generation."
)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtills jwtUtills;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(),request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtills.generateTokenForUser(authentication);
            ShopUserDetails userDetails=(ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),jwt);
            return ResponseEntity.ok(new ApiResponse("Login Success!",jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
