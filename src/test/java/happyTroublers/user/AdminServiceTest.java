package happyTroublers.user;

import happyTroublers.destination.DestinationRepository;
import happyTroublers.exceptions.custom_exceptions.UserNotFoundException;
import happyTroublers.user.dtos.AdminRequest;
import happyTroublers.user.dtos.AdminResponse;
import happyTroublers.user.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static happyTroublers.user.Role.ADMIN;
import static happyTroublers.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock
    private CustomUserRepository customUserRepository;
    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private AdminService adminService;

    private CustomUser user;
    private List<CustomUser> usersList;

    @BeforeEach
    void setUp() {
        user = new CustomUser(1L, "María", "maria@gmail.com", "maria123", USER, List.of());
    }

    @Test
    void getAllUsers_whenUserExist_returnsListOfUserResponse() {
        when(customUserRepository.findAll()).thenReturn(List.of(user));

        List<AdminResponse> result = adminService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("María", result.getFirst().username());
        assertEquals("maria@gmail.com", result.getFirst().email());

        verify(customUserRepository, times(1)).findAll();
    }

    @Test
    void getUserById_whenUserExists_returnsUserResponse() {
        when(customUserRepository.findById(1L)).thenReturn(Optional.of(user));

        AdminResponse result = adminService.getUserById(1L);

        assertEquals("María", result.username());
        assertEquals("maria@gmail.com", result.email());

        verify(customUserRepository).findById(1L);
    }

    @Test
    void getUserById_whenUserNotFound_throwsException() {
        when(customUserRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adminService.getUserById(2L));
    }

    @Test
    void updateUser_updatesUserSuccessfully() {
        AdminRequest request = new AdminRequest("MariaUpdated", "maria.updated@gmail.com", "newpass123", ADMIN);

        when(customUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(customUserRepository.save(any())).thenReturn(user);

        AdminResponse result = adminService.updateUser(1L, request);

        assertEquals("MariaUpdated", result.username());
        assertEquals("maria.updated@gmail.com", result.email());
        assertEquals("newpass123", user.getPassword());
        assertEquals(ADMIN, result.role());

        verify(customUserRepository).save(user);
    }

    @Test
    void deleteUser_whenUserExists_deleteSuccessfully() {
        when(customUserRepository.findById(1L)).thenReturn(Optional.of(user));

        adminService.deleteUser(1L);

        verify(customUserRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_whenUserNotFound_throwsException() {
        when(customUserRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adminService.deleteUser(2L));
    }
}
