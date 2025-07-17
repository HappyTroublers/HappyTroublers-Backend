package happyTroublers.destination.dtos;
import happyTroublers.destination.Destination;
import happyTroublers.user.CustomUser;

public class DestinationMapper {
    public static Destination dtoToEntity(DestinationRequest dto, CustomUser user) {
        return new Destination(
                dto.city(),
                dto.country(),
                dto.description(),
                dto.imageUrl(),
                user
        );
    }

    public static DestinationResponse entityToDto(Destination destination) {
        return new DestinationResponse(
                destination.getCity(),
                destination.getCountry(),
                destination.getDescription(),
                destination.getImageUrl(),
                destination.getUser().getUsername()
        );
    }
}
