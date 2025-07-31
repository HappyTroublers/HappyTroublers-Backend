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
    private final CustomUserRepository customUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(CustomUserRepository customUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AdminResponse> getAllUsers() {
        List<CustomUser> users = customUserRepository.findAll();
        return users.stream().map(user -> AdminMapper.entityToDto(user)).toList();
    }

    public AdminResponse getUserById(Long id) {
        CustomUser user = customUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return AdminMapper.entityToDto(user);
    }

    public AdminResponse addUser(AdminRequest adminRequest){
        CustomUser newUser = AdminMapper.dtoToEntity(adminRequest);
        newUser.setPassword(passwordEncoder.encode(adminRequest.password()));
        CustomUser savedUser = customUserRepository.save(newUser);
        return AdminMapper.entityToDto(savedUser);
    }

    public AdminResponse updateUser(Long id, AdminRequest adminRequest) {
        CustomUser existingUser = customUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        existingUser.setUsername(adminRequest.username());
        existingUser.setEmail(adminRequest.email());
        existingUser.setPassword(passwordEncoder.encode(adminRequest.password()));
        existingUser.setRole(adminRequest.role());
        CustomUser updatedUser = customUserRepository.save(existingUser);
        return AdminMapper.entityToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        getUserById(id);
        customUserRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository.findByUsername(username)
                .map(user-> new CustomUserDetail(user))
                .orElseThrow(() -> new UserNotFoundException(username));

    }
}
