package happyTroublers.destination.dtos;

import happyTroublers.user.CustomUser;

public record DestinationResponse(
        String city,
        String country,
        String description,
        String imageUrl,
        String username
) {
}

