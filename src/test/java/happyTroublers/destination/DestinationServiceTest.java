package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationMapper;
import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.exceptions.custom_exceptions.DestinationNotFoundException;
import happyTroublers.exceptions.custom_exceptions.UserNotFoundException;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static happyTroublers.user.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;
    @Mock
    private CustomUserRepository customUserRepository;

    @InjectMocks
    private DestinationService destinationService;

    private CustomUser user;
    private Destination destination;
    private List<Destination> destinationList;
    private DestinationRequest destinationRequest;
    private String username;
    private Long id;

    @BeforeEach
    void setUp() {
        user = new CustomUser(1L, "María", "maria@gmail.com", "maria123", USER, List.of());
        destination = new Destination (1L, "Madrid", "Spain", "blablabla", "img.png", user);
        destinationList = List.of(destination);
        destinationRequest = new DestinationRequest("Madrid", "Spain", "blablabla", "img.png", "María");
        username = "María";
        id = 1L;
    }

    @Test
    void getAllDestinations_whenDestinationExist_returnListOfDestinationResponse() {

        when(destinationRepository.findAll()).thenReturn(List.of(destination));

        List<DestinationResponse> result = destinationService.getAllDestinations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Madrid", result.getFirst().city());
        assertEquals("Spain", result.getFirst().country());
        assertEquals("blablabla", result.getFirst().description());
        assertEquals("img.png", result.getFirst().imageUrl());
        assertEquals("María", result.getFirst().username());

        verify(destinationRepository, times(1)).findAll();
    }

    @Test
    void getDestinationById_whenDestinationExist_returnDestinationResponse() {

        //Long id = 1L;
        //CustomUser user = new CustomUser(1L, "María", "maria@email.com", "pass123", USER, List.of());
        //Destination destination = new Destination(id, "Madrid", "España", "Una ciudad genial", "img.png", user);
        DestinationResponse expectedResponse = DestinationMapper.entityToDto(destination);

        given(destinationRepository.findById(id)).willReturn(Optional.of(destination));

        DestinationResponse result = destinationService.getDestinationById(id);

        assertThat(result.city()).isEqualTo(expectedResponse.city());
        assertThat(result.country()).isEqualTo(expectedResponse.country());
        assertThat(result.description()).isEqualTo(expectedResponse.description());
        assertThat(result.imageUrl()).isEqualTo(expectedResponse.imageUrl());
        assertThat(result.username()).isEqualTo(expectedResponse.username());

        verify(destinationRepository, times(1)).findById(id);
    }

    @Test
    void getDestinationById_whenDestinationDoesNotExist_throwsException() {

        Long id = 2L;

        String messageExpected = "Destination with id " + id + " not found";

        when(destinationRepository.findById((eq(id)))).thenReturn(Optional.empty());

        Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.getDestinationById(id));

        assertEquals(messageExpected, result.getMessage());

        verify(destinationRepository, times(1)).findById(id);
    }

    @Test
    void getDestinationsByUsername_whenDestinationExist_returnListOfDestinationResponse() {

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(destinationRepository.findByUser(user)).thenReturn(Optional.of(destinationList));

        List<DestinationResponse> result = destinationService.getDestinationsByUsername(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Madrid", result.getFirst().city());
        assertEquals("Spain", result.getFirst().country());
        assertEquals("blablabla", result.getFirst().description());
        assertEquals("img.png", result.getFirst().imageUrl());
        assertEquals("María", result.getFirst().username());

        verify(destinationRepository, times(1)).findByUser(user);
        verify(customUserRepository, times(1)).findByUsername("María");
    }

    @Test
    void addDestination_whenCorrectRequest_returnDestinationResponse() {

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(destinationRepository.save(any(Destination.class))).thenReturn(destination);

        DestinationResponse result = destinationService.addDestination(destinationRequest);

        assertNotNull(result);
        assertEquals(DestinationResponse.class, result.getClass());
        assertEquals("Madrid", result.city());
        assertEquals("Spain", result.country());
        assertEquals("blablabla", result.description());
        assertEquals("img.png", result.imageUrl());
        assertEquals("María", result.username());

        verify(customUserRepository, times(1)).findByUsername(username);
        verify(destinationRepository, times(1)).save(any(Destination.class));
    }

    @Test
    void addDestination_whenUsernameNotFound_throwsException() {

        username = "Pepa";
        destinationRequest = new DestinationRequest("Tokio", "Japan", "blublublu", "img3.png", "Pepa");
        String messageExpected = "User " + destinationRequest.username() + " not found";
        when(customUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception result = assertThrows(UserNotFoundException.class, () -> destinationService.addDestination(destinationRequest));

        assertEquals(messageExpected, result.getMessage());

        verify(customUserRepository, times(1)).findByUsername(username);
        verify(destinationRepository, never()).save(any());
    }

    @Test
    void updateDestination_whenDestinationExist_returnDestinationResponse() {

        DestinationRequest updatedDestinationRequest = new DestinationRequest("London", "UK", "blibli", "img1.png", "María");
        Destination updatedDestination = new Destination(1L, "London", "UK", "blibli", "img1.png", user);
        DestinationResponse updatedDestinationResponse = new DestinationResponse("London", "UK", "blibli", "img1.png", "María");

        when(destinationRepository.findById(eq(id))).thenReturn(Optional.of(destination));
        when(destinationRepository.save(any(Destination.class))).thenReturn(updatedDestination);

        DestinationResponse result = destinationService.updateDestination(id, updatedDestinationRequest);

        assertEquals(updatedDestinationResponse, result);

        verify(destinationRepository,times(1)).findById(eq(id));
        verify(destinationRepository, times(1)).save(any(Destination.class));
    }

    @Test
    void updateDestination_whenDestinationDoesNotExist_throwsException() {

        Long id = 2L;
        DestinationRequest updatedDestinationRequest = new DestinationRequest("London", "UK", "blibli", "img1.png", "María");

        String messageExpected = "Destination with id " + id + " not found";

        when(destinationRepository.findById((eq(id)))).thenReturn(Optional.empty());

        Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.updateDestination(id, updatedDestinationRequest));

        assertEquals(messageExpected, result.getMessage());

        verify(destinationRepository, times(1)).findById(id);
        verify(destinationRepository, never()).save(any());
    }

    @Test
    void deleteDestination_whenDestinationExists_deletesSuccessfully() {
        //Long id = 1L;
        //Destination destination = new Destination(id, "Madrid", "Spain", "Description", "img.png", user);

        when(destinationRepository.findById(id)).thenReturn(Optional.of(destination));

        destinationService.deleteDestination(id);

        verify(destinationRepository, times(1)).deleteById(id);
        verify(destinationRepository, times(1)).findById(id);
    }

   @Test
    void deleteDestination_whenDestinationDoesNotExist_throwsException() {

        Long id = 2L;

       String messageExpected = "Destination with id " + id + " not found";

       when(destinationRepository.findById((eq(id)))).thenReturn(Optional.empty());

       Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.deleteDestination(id));

       assertEquals(messageExpected, result.getMessage());

       verify(destinationRepository, times(1)).findById(id);
       verify(destinationRepository, never()).deleteById(anyLong());
   }
}

