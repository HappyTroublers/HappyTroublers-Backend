package happyTroublers.destintion;

import happyTroublers.destination.DestinationRepository;
import happyTroublers.destination.DestinationService;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.CustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static happyTroublers.user.Role.USER;

@ExtendWith((MockitoExtension.class))
public class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private CustomUser user;
    private DestinationResponse destinationResponse;

    @BeforeEach
    void setUp() {
        user = new CustomUser(1L, "María", "maria@gmail.com", "maria123", USER, List.of());
        destinationResponse = new DestinationResponse( "Madrid", "Spain", "blablabla", "img.png", "María");
    }

    @Test
    void getDestinationByUser_whenDestinationExist_returnDestinationResponse() {


    }
}
