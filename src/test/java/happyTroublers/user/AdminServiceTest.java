package happyTroublers.user;

import happyTroublers.destination.DestinationRepository;
import happyTroublers.user.dtos.AdminResponse;
import happyTroublers.user.dtos.UserResponse;
import happyTroublers.user.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static happyTroublers.user.Role.USER;
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
    void getAllUsers_whenUserExist_returnListOfUserResponse() {

        when(customUserRepository.findAll()).thenReturn(List.of(user)); //Simula que encuentra usuarios

        List<AdminResponse> result = adminService.getAllUsers(); //lista los usuarios con los parametros del dto adminresponse

        verify(customUserRepository, times(1)).findAll(); //verifica si el metodo findAll fue llamado
    }

    void deleteUser_whenUserExists_deleteSuccess() {
        Long id = 1L;
        CustomUser user = new CustomUser(1L, "Bob", "Esponja@email.com", "pass123", USER, List.of());

        when(customUserRepository.findById(id)).thenReturn(Optional.of(user));

        adminService.deleteUser(id);

        verify(customUserRepository, times(1)).delete(user);
    }
}
