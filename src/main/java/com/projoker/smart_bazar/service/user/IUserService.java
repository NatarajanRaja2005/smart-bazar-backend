package com.projoker.smart_bazar.service.user;

import com.projoker.smart_bazar.dto.UserDto;
import com.projoker.smart_bazar.model.User;
import com.projoker.smart_bazar.request.CreateUserRequest;
import com.projoker.smart_bazar.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    User getAuthenticatedUser();

    UserDto convertUserToDto(User user);
}
