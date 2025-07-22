package happyTroublers.user.services;

import happyTroublers.exceptions.custom_exceptions.UserNotFoundException;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import happyTroublers.user.dtos.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService {
    public final CustomUserRepository CUSTOM_USER_REPOSITORY;

    public CustomUserService(CustomUserRepository customUserRepository) {
        CUSTOM_USER_REPOSITORY = customUserRepository;
    }

    public UserResponse getUserByName(String username) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));
        return UserMapper.entityToDto(user);
    }
}