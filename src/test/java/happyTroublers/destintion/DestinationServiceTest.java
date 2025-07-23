package happyTroublers.destintion;

import happyTroublers.destination.Destination;
import happyTroublers.destination.DestinationRepository;
import happyTroublers.destination.DestinationService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private DestinationResponse destinationResponse;

    @BeforeEach
    void setUp() {
        user = new CustomUser(1L, "María", "maria@gmail.com", "maria123", USER, List.of());
        destination = new Destination (1L, "Madrid", "Spain", "blablabla", "img.png", user);
        destinationResponse = new DestinationResponse( "Madrid", "Spain", "blablabla", "img.png", "María");
        destinationList = List.of(destination);
    }

    @Test
    void getDestinationByUser_whenDestinationExist_returnListOfDestinationResponse() {

        String username = "María";

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(destinationRepository.findByUser(user)).thenReturn(Optional.of(destinationList));

        List<DestinationResponse> result = destinationService.getDestinationsByUsername(username);

        assertEquals(1, result.size());
        assertEquals("Madrid", result.getFirst().city());
        assertEquals("Spain", result.getFirst().country());
        assertEquals("blablabla", result.getFirst().description());
        assertEquals("img.png", result.getFirst().imageUrl());
        assertEquals("María", result.getFirst().username());

        verify(destinationRepository, times(1)).findByUser(user);
        verify(customUserRepository, times(1)).findByUsername("María");
    }
}
