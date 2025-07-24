package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationMapper;
import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    //private DestinationResponse destinationResponse;
    private DestinationRequest destinationRequest;
    private String username;
    private Long id;

    @BeforeEach
    void setUp() {
        user = new CustomUser(1L, "María", "maria@gmail.com", "maria123", USER, List.of());
        destination = new Destination (1L, "Madrid", "Spain", "blablabla", "img.png", user);
        destinationList = List.of(destination);
        //destinationResponse = new DestinationResponse( "Madrid", "Spain", "blablabla", "img.png", "María");
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
        // Given
        Long id = 1L;
        CustomUser user = new CustomUser(1L, "María", "maria@email.com", "pass123", USER, List.of());
        Destination destination = new Destination(id, "Madrid", "España", "Una ciudad genial", "img.png", user);
        DestinationResponse expectedResponse = DestinationMapper.entityToDto(destination);

        given(destinationRepository.findById(id)).willReturn(Optional.of(destination));

        // When
        DestinationResponse result = destinationService.getDestinationById(id);

        // Then
        assertThat(result.city()).isEqualTo(expectedResponse.city());
        assertThat(result.country()).isEqualTo(expectedResponse.country());
        assertThat(result.description()).isEqualTo(expectedResponse.description());
        assertThat(result.imageUrl()).isEqualTo(expectedResponse.imageUrl());
        assertThat(result.username()).isEqualTo(expectedResponse.username());

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
}
