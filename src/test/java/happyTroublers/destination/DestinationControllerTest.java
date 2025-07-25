package happyTroublers.destination;

import com.fasterxml.jackson.databind.ObjectMapper;
import happyTroublers.destination.dtos.DestinationResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DestinationService destinationService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<DestinationResponse> destinationResponseList;

    @BeforeEach
    void setUp() {

        destinationResponseList = new ArrayList<>();

        DestinationResponse destinationResponse1 = new DestinationResponse("Madrid", "Spain", "blablabla", "img.png", "María");
        DestinationResponse destinationResponse2 = new DestinationResponse("London", "UK", "blibli", "img1.png", "María");

        destinationResponseList.add(destinationResponse1);
        destinationResponseList.add(destinationResponse2);
    }

    @Test
    void shouldGetAllDestinationsSuccessfully() throws Exception {

        given(destinationService.getAllDestinations()).willReturn(destinationResponseList);

        mockMvc.perform(get("/destinations").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].city").value("Madrid"))
                .andExpect(jsonPath("$[1].city").value("London"))
                .andExpect(jsonPath("$[0].username").value("María"));
    }
}
