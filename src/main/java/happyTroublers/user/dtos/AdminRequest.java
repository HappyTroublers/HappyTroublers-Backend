package happyTroublers.user.dtos;

import happyTroublers.user.Role;
import jakarta.validation.constraints.*;

public record AdminRequest (
        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 50, message = "Username must contain between 2 and 50 characters")
        String username,

        @NotBlank(message = "E-mail is required")
        @Email(message = "Invalid e-mail format",
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @NotBlank(message = "Password is required")
        @Pattern(message = "Password must contain a minimum of 12 characters, including a number, one uppercase letter, one lowercase letter and one special character",
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{12,}$")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
