package happyTroublers.user.services;

import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import happyTroublers.user.dtos.AdminMapper;
import happyTroublers.user.dtos.AdminRequest;
import happyTroublers.user.dtos.AdminResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    public final CustomUserRepository
    CUSTOM_USER_REPOSITORY;


    public AdminService(CustomUserRepository customUserRepository) {
        CUSTOM_USER_REPOSITORY = customUserRepository;
    }

    public List<AdminResponse> getAllUsers() {
        List<CustomUser> users = CUSTOM_USER_REPOSITORY.findAll();
        return users.stream().map(user -> AdminMapper.entityToDto(user)).toList();
    }

    public AdminResponse getUserById(Long id) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return AdminMapper.entityToDto(user);
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
}
