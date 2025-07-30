package happyTroublers.user.dtos;

import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.Role;

import java.util.List;

public record AdminResponse(
        String username,
        String email,
        Role role,
        List<DestinationResponse> destinations
) {
}
