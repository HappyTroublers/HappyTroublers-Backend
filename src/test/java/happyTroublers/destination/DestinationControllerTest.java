package happyTroublers.destination;

import com.fasterxml.jackson.databind.ObjectMapper;
import happyTroublers.destination.dtos.DestinationResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DestinationService destinationService;

    private DestinationResponse destinationResponse;
    private static final String BASE_URL = "/destinations";

    @BeforeEach
    void setUp() {
        destinationResponse = new DestinationResponse("Madrid", "Spain", "blablabla", "img.png", "María");
    }

    @Nested
    @DisplayName("GET endpoints")
    class GetUserTests {

        @Test
        @DisplayName("GET /destinations - should return all destinations")
        void getAllDestinations_returnsListOfDestinations() throws Exception {
            List<DestinationResponse> list = List.of(destinationResponse);
            given(destinationService.getAllDestinations()).willReturn(list);

            mockMvc.perform(get("/destinations").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$[0].city").value("Madrid"))
                    .andExpect(jsonPath("$[0].country").value("Spain"))
                    .andExpect(jsonPath("$[0].username").value("María"));
        }

        @Test
        @DisplayName("GET /destinations/{id} - should return destination by ID")
        void getDestinationById_returnsCorrectDestination() throws Exception {
            given(destinationService.getDestinationById(1L, "María")).willReturn(destinationResponse);

            mockMvc.perform(get("/destinations/1").param("username", "María"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.city").value("Madrid"))
                    .andExpect(jsonPath("$.country").value("Spain"))
                    .andExpect(jsonPath("$.username").value("María"));
        }
    }
}
