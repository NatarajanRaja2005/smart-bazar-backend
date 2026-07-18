package com.projoker.smart_bazar.service.user;

import com.projoker.smart_bazar.Repository.UserRepository;
import com.projoker.smart_bazar.dto.UserDto;
import com.projoker.smart_bazar.exception.AlreadyExistsException;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.User;
import com.projoker.smart_bazar.request.CreateUserRequest;
import com.projoker.smart_bazar.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                        .map(req ->{
                            User user=new User();
                            user.setFirstName(request.getFirstName());
                            user.setLastName(request.getLastName());
                            user.setEmail(request.getEmail());
                            user.setPassword(passwordEncoder.encode(request.getPassword()));

                            return userRepository.save(user);
                        }).orElseThrow(() -> new AlreadyExistsException("Oops!"+request.getEmail()+"was already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {

        return userRepository.findById(userId).map(existUser -> {
            existUser.setFirstName(request.getFirstName());
            existUser.setLastName(request.getLastName());
            return userRepository.save(existUser);
        }).orElseThrow(()->new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,()->{
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto convertUserToDto(User user){
        System.out.println("Before mapping " +user.getOrder());
        System.out.println(user.getOrder().size());
        return modelMapper.map(user,UserDto.class);
    }

}
