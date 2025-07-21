package happyTroublers.user.dtos;

import happyTroublers.destination.Destination;

import java.util.List;

public record UserRequest(
        String username,
        String email,
        String password
) {
}
