package com.quickbite.UserService.service;

import com.quickbite.UserService.dto.ProfileUpdateRequestDTO;
import com.quickbite.UserService.dto.UserDTO;
import com.quickbite.UserService.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDTO profileUpdateRequestDto);

    UserDTO getMyProfile();
}
