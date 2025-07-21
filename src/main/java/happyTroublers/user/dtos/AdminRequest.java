package happyTroublers.user.dtos;

import happyTroublers.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequest (
        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 50, message = "Username must contain between 2 and 50 characters")
        String username,
        @NotBlank(message = "E-mail is required")
        @Email
        @Size(min = 15, max = 50, message = "E-mail must contain between 15 and 50 characters")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 12, max = 50, message = "Password must contain between 20 and 50 characters")
        String password,
        @NotBlank(message = "Role is required")
        @Size(max = 20, message = "Role must have a maximum of 20 characters")
        Role role
) {
}
