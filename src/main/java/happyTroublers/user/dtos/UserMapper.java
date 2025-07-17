package happyTroublers.user.dtos;

import happyTroublers.destination.Destination;
import happyTroublers.user.CustomUser;

import java.util.List;

public class UserMapper {
    public static CustomUser dtoToEntity(UserRequest dto) {
        return new CustomUser(
                dto.username(),
                dto.email(),
                dto.password()
        );
    }

    public static UserResponse entityToDto (CustomUser user, List<Destination> destinationsList) {
        return new UserResponse(
                user.getUsername(),
                destinationsList
        );
    }
}
