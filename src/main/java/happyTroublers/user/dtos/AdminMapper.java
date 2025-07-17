package happyTroublers.user.dtos;

import happyTroublers.destination.dtos.DestinationMapper;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.CustomUser;

import java.util.List;

public class AdminMapper {
    public static CustomUser dtoToEntity(AdminRequest dto) {
        return new CustomUser(
                dto.username(),
                dto.email(),
                dto.password(),
                dto.role()
        );
    }

    //DestinationList in admin?
    public static AdminResponse entityToDto(CustomUser user) {
        List<DestinationResponse> destinationsList = user.getDestinations().stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
        return new AdminResponse(user.getUsername(), user.getEmail(), user.getRole(), destinationsList);
    }
}
