package happyTroublers.user.services;

import happyTroublers.exceptions.custom_exceptions.UserNotFoundException;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserDetail;
import happyTroublers.user.CustomUserRepository;
import happyTroublers.user.dtos.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements UserDetailsService {
    private final CustomUserRepository CUSTOM_USER_REPOSITORY;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(CustomUserRepository customUserRepository, BCryptPasswordEncoder passwordEncoder) {
        CUSTOM_USER_REPOSITORY = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AdminResponse> getAllUsers() {
        List<CustomUser> users = CUSTOM_USER_REPOSITORY.findAll();
        return users.stream().map(user -> AdminMapper.entityToDto(user)).toList();
    }

    public AdminResponse getUserById(Long id) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return AdminMapper.entityToDto(user);
    }

    public UserResponse addUser(UserRequest userRequest){
        CustomUser newUser = UserMapper.dtoToEntity(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        CustomUser savedUser = CUSTOM_USER_REPOSITORY.save(newUser);
        return UserMapper.entityToDto(savedUser);
    }

    public AdminResponse updateUser(Long id, AdminRequest adminRequest) {
        CustomUser existingUser = CUSTOM_USER_REPOSITORY.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return CUSTOM_USER_REPOSITORY.findByUsername(username)
                .map(user-> new CustomUserDetail(user))
                .orElseThrow(() -> new UserNotFoundException(username));

    }
}
