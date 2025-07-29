package happyTroublers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import happyTroublers.user.dtos.AdminRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/admin/users";

    @Nested
    @DisplayName("GET endpoints")
    class GetUserTests {

        @Test
        @DisplayName("GET /admin/users - should return all users")
        @WithMockUser(username = "Belen", roles = {"USER"})
        void getAllUsers_returnsListOfUsers() throws Exception {
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].username").value("Belen"))
                    .andExpect(jsonPath("$[1].email").value("mariya@hot.com"));
        }

        @Test
        @DisplayName("GET /admin/users/1 - should return user by ID")
        @WithMockUser(username = "Belen", roles = {"USER"})
        void getUserById_returnsCorrectUser() throws Exception {
            mockMvc.perform(get(BASE_URL + "/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("Belen"))
                    .andExpect(jsonPath("$.email").value("belen@correo.com"))
                    .andExpect(jsonPath("$.role").value("USER"));
        }

        @Test
        @DisplayName("GET /admin/users/99 - returns error if user not found")
        @WithMockUser(username = "Belen", roles = {"USER"})
        void getUserById_returnsNotFound_whenIdDoesNotExist() throws Exception {
            mockMvc.perform(get(BASE_URL + "/99"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"));
        }
    }

    @Nested
    @DisplayName("PUT endpoint")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class UpdateUserTests {

        @Test
        @DisplayName("Should update an existing user correctly and return 200 OK")
        @WithMockUser(username = "Belen", roles = {"USER"})
        void updateUser_ReturnsOkAndUpdatedUser() throws Exception {
            AdminRequest update = new AdminRequest(
                    "BelenUpdated",
                    "updated@correo.com",
                    "Updatedpassword123.",
                    Role.USER
            );

            mockMvc.perform(put(BASE_URL + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("BelenUpdated"))
                    .andExpect(jsonPath("$.role").value("USER"));
        }
    }
}
