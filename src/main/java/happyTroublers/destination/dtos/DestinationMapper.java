package happyTroublers.destination.dtos;
import happyTroublers.destination.Destination;
import happyTroublers.user.CustomUser;

public class DestinationMapper {
    public static DestinationResponse entityToDto(Destination destination) {
        return new DestinationResponse(
                destination.getCity(),
                destination.getCountry(),
                destination.getDescription(),
                destination.getImageUrl(),
                destination.getUser().getUsername()
        );
}
    public static Destination dtoToEntity(DestinationResponse destinationResponse, CustomUser user) {
        return new Destination(
                destinationResponse.city(),
                destinationResponse.country(),
                destinationResponse.description(),
                destinationResponse.imageUrl(),
                user
        );
    }
}
