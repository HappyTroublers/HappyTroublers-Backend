package happyTroublers.user;

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

    public List<AdminResponse> getAllUsers() {
        List<CustomUser> users = CUSTOM_USER_REPOSITORY.findAll();
        return users.stream().map(user -> AdminMapper.entityToDto(user)).toList();
    }

    public UserResponse getUserByName(String username) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
        return UserMapper.entityToDto(user);
    }

    public AdminResponse updateUser(Long id, AdminRequest adminRequest) {
        CustomUser existingUser = CUSTOM_USER_REPOSITORY.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        existingUser.setUsername(adminRequest.username());
        existingUser.setEmail(adminRequest.email());
        existingUser.setPassword(adminRequest.password());
        existingUser.setRole(adminRequest.role());
        CustomUser updatedUser = CUSTOM_USER_REPOSITORY.save(existingUser);
        return AdminMapper.entityToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        getUserById(id);
        CUSTOM_USER_REPOSITORY.deleteById(id);
    }

    public UserResponse getUserById(Long id) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return CustomUserMapper.UserMapper.entityToDto(user);
    }
}
