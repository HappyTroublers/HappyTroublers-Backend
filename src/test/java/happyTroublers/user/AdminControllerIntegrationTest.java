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
        @WithMockUser(username = "Blackbeard", roles = {"ADMIN"})
        void getAllUsers_returnsListOfUsers() throws Exception {
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(10)))
                    .andExpect(jsonPath("$[0].username").value("Blackbeard"))
                    .andExpect(jsonPath("$[1].email").value("anne@pirates.com"));
        }

        @Test
        @DisplayName("GET /admin/users/2 - should return user by ID")
        @WithMockUser(username = "Blackbeard", roles = {"ADMIN"})
        void getUserById_returnsCorrectUser() throws Exception {
            mockMvc.perform(get(BASE_URL + "/2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("AnneBonny"))
                    .andExpect(jsonPath("$.email").value("anne@pirates.com"))
                    .andExpect(jsonPath("$.role").value("USER"));
        }

        @Test
        @DisplayName("GET /admin/users/99 - returns error if user not found")
        @WithMockUser(username = "Blackbeard", roles = {"ADMIN"})
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
        @DisplayName("PUT admin/users/2 - should update an existing user correctly")
        @WithMockUser(username = "Blackbeard", roles = {"ADMIN"})
        void updateUser_ReturnsOkAndUpdatedUser() throws Exception {
            AdminRequest update = new AdminRequest(
                    "ElisabethSwan",
                    "updated@mail.com",
                    "Updatedpassword123.",
                    Role.USER
            );

            mockMvc.perform(put(BASE_URL + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("ElisabethSwan"))
                    .andExpect(jsonPath("$.email").value("updated@mail.com"))
                    .andExpect(jsonPath("$.role").value("USER"));
        }
    }

    @Nested
    @DisplayName("DELETE endpoint")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class DeleteUserTests {

        @Test
        @DisplayName("DELETE admin/users/2 - should delete an existing user correctly")
        @WithMockUser(username = "Blackbeard", roles = {"ADMIN"})
        void deleteUser_ReturnsNoContent() throws Exception {
            mockMvc.perform(delete(BASE_URL + "/2"))
                    .andExpect(status().isNoContent());

            mockMvc.perform(get(BASE_URL + "/2"))
                    .andExpect(status().isNotFound());
        }
    }
}
