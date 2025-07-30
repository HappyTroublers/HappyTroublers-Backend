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
import org.mockito.Mockito;
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
        destinationRequest = new DestinationRequest("Madrid", "Spain", "blablabla", "img.png");
        username = "María";
        id = 1L;
    }

    @Test
    void getAllDestinations_whenDestinationExist_returnsListOfDestinationResponse() {

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
    void getDestinationById_whenDestinationExist_returnsDestinationResponse() {

        DestinationResponse expectedResponse = DestinationMapper.entityToDto(destination);

        given(destinationRepository.findById(id)).willReturn(Optional.of(destination));

        DestinationResponse result = destinationService.getDestinationById(id, username);

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

        Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.getDestinationById(id, username));

        assertEquals(messageExpected, result.getMessage());

        verify(destinationRepository, times(1)).findById(id);
    }

    @Test
    void getDestinationsByUsername_whenDestinationExist_returnsListOfDestinationResponse() {

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(destinationRepository.findByUser(user)).thenReturn(destinationList);

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
    void addDestination_whenCorrectRequest_returnsDestinationResponse() {

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(destinationRepository.save(any(Destination.class))).thenReturn(destination);

        DestinationResponse result = destinationService.addDestination(destinationRequest, username);

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
        destinationRequest = new DestinationRequest("Tokio", "Japan", "blublublu", "img3.png");

        String messageExpected = "User " + username + " not found";

        when(customUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception result = assertThrows(UserNotFoundException.class, () -> destinationService.addDestination(destinationRequest, username));

        assertEquals(messageExpected, result.getMessage());

        verify(customUserRepository, times(1)).findByUsername(username);
        verify(destinationRepository, never()).save(any());
    }

    @Test
    void updateDestination_whenDestinationExist_returnsDestinationResponse() {

        DestinationRequest updatedDestinationRequest = new DestinationRequest("London", "UK", "blibli", "img1.png");
        Destination updatedDestination = new Destination(1L, "London", "UK", "blibli", "img1.png", user);
        DestinationResponse updatedDestinationResponse = new DestinationResponse("London", "UK", "blibli", "img1.png", "María");

        when(destinationRepository.findByUserUsername(username)).thenReturn(List.of(destination));
        when(destinationRepository.save(any(Destination.class))).thenReturn(updatedDestination);

        DestinationResponse result = destinationService.updateDestination(id, updatedDestinationRequest, username);

        assertEquals(updatedDestinationResponse, result);

        verify(destinationRepository,times(1)).findByUserUsername(username);
        verify(destinationRepository, times(1)).save(destination);
    }

    @Test
    void updateDestination_whenDestinationDoesNotExist_throwsException() {

        DestinationRequest updatedDestinationRequest = new DestinationRequest("London", "UK", "blibli", "img1.png");

        String messageExpected = "Destination with id " + id + " does not belong to user " + username;

        when(destinationRepository.findByUserUsername(username)).thenReturn(List.of());

        Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.updateDestination(id, updatedDestinationRequest, username));

        assertEquals(messageExpected, result.getMessage());

        verify(destinationRepository, times(1)).findByUserUsername(username);
        verify(destinationRepository, never()).save(any());
    }

    @Test
    void deleteDestination_whenDestinationExists_deletesSuccessfully() {

        when(destinationRepository.findByUserUsername(username)).thenReturn(List.of(destination));

        destinationService.deleteDestination(id, username);

        verify(destinationRepository, times(1)).findByUserUsername(username);
        verify(destinationRepository, times(1)).delete(destination);
    }

   @Test
   void deleteDestination_whenDestinationDoesNotExist_throwsException() {
       when(destinationRepository.findByUserUsername(username)).thenReturn(List.of());

        String messageExpected = "Destination with id " + id + " does not belong to user " + username;

        Exception result = assertThrows(DestinationNotFoundException.class, () -> destinationService.deleteDestination(id, username));

        assertEquals(messageExpected, result.getMessage());

        verify(destinationRepository, times(1)).findByUserUsername(username);
        verify(destinationRepository, never()).delete(any());
   }

   @Test
   void filterByCity_whenCityExists_returnsListOfDestinationResponse() {

        String city = "Madrid";
        List<DestinationResponse> expected = List.of(DestinationMapper.entityToDto(destination));

        when(destinationRepository.findByCityIgnoreCase(city)).thenReturn(List.of(destination));

        List<DestinationResponse> result = destinationService.filterByCity(city);

        assertEquals(expected, result);

        verify(destinationRepository, times(1)).findByCityIgnoreCase(city);
   }

   @Test
    void filterByCity_whenCityDoesNotExist_returnsEmptyList() {

        String city = "NoCity";

        when(destinationRepository.findByCityIgnoreCase(city)).thenReturn(List.of());

        List<DestinationResponse> result = destinationService.filterByCity(city);

        assertTrue(result.isEmpty());
   }

    @Test
    void filterByCountry_whenCountryExists_returnsListOfDestinationResponse() {

        String country = "Spain";
        List<DestinationResponse> expected = List.of(DestinationMapper.entityToDto(destination));

        when(destinationRepository.findByCountryIgnoreCase(country)).thenReturn(List.of(destination));

        List<DestinationResponse> result = destinationService.filterByCountry(country);

        assertEquals(expected, result);

        verify(destinationRepository, times(1)).findByCountryIgnoreCase(country);
    }

    @Test
    void filterByCountry_whenCountryDoesNotExist_returnsEmptyList() {

        String country = "NoCountry";

        when(destinationRepository.findByCountryIgnoreCase(country)).thenReturn(List.of());

        List<DestinationResponse> result = destinationService.filterByCountry(country);

        assertTrue(result.isEmpty());
    }

    @Test
    void filterDestinations_whenCityGiven_callsFilterByCityOnly() {

        String city = "Madrid";
        List<DestinationResponse> expected = List.of(DestinationMapper.entityToDto(destination));

        DestinationService spyService = Mockito.spy(destinationService);
        doReturn(expected).when(spyService).filterByCity(city);

        List<DestinationResponse> result = spyService.filterDestinations(city, null);

        assertEquals(expected, result);

        verify(spyService, times(1)).filterByCity(city);
        verify(spyService, never()).filterByCountry(any());
        verify(spyService, never()).getAllDestinations();
    }

    @Test
    void filterDestinations_whenCountryGiven_callsFilterByCountryOnly() {

        String country = "Spain";
        List<DestinationResponse> expected = List.of(DestinationMapper.entityToDto(destination));

        DestinationService spyService = Mockito.spy(destinationService);
        doReturn(expected).when(spyService).filterByCountry(country);

        List<DestinationResponse> result = spyService.filterDestinations(null, country);

        assertEquals(expected, result);

        verify(spyService, times(1)).filterByCountry(country);
        verify(spyService, never()).filterByCity(any());
        verify(spyService, never()).getAllDestinations();
    }

    @Test
    void filterDestinations_whenNoCityOrCountry_callsGetAllDestinations() {

        List<DestinationResponse> expected = List.of(DestinationMapper.entityToDto(destination));

        DestinationService spyService = Mockito.spy(destinationService);
        doReturn(expected).when(spyService).getAllDestinations();

        List<DestinationResponse> result = spyService.filterDestinations(null, null);

        assertEquals(expected, result);

        verify(spyService, times(1)).getAllDestinations();
        verify(spyService, never()).filterByCity(any());
        verify(spyService, never()).filterByCountry(any());
    }
}



