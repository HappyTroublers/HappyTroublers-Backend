package happyTroublers.user.dtos;

import happyTroublers.destination.Destination;

import java.util.List;

public record UserResponse(
        String username,
        List<Destination> destinations
) {
}
