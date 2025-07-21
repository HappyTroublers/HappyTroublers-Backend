package happyTroublers.user.services;

import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import happyTroublers.user.dtos.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService {
    public final CustomUserRepository CUSTOM_USER_REPOSITORY;

    public CustomUserService(CustomUserRepository customUserRepository) {
        CUSTOM_USER_REPOSITORY = customUserRepository;
    }

    public UserResponse getUserByName(String username) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
        return UserMapper.entityToDto(user);
    }
}
