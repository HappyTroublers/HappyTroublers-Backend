package happyTroublers.destination.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Singular;

public record DestinationRequest(
        @NotBlank(message = "City is required")
        @Size(min = 2, max = 100, message = "City must contain between 2 and 100 characters")
        String city,

        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 100, message = "Country must contain between 2 and 100 characters")
        String country,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @Pattern(regexp = "^(https?|ftp)://[a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=.]+$",
                message = "Image URL must be a valid URL")
        String imageUrl,

        String username
) {
}
