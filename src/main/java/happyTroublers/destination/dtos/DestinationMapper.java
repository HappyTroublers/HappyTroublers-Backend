package happyTroublers.destination.dtos;

import happyTroublers.destination.Destination;

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
    public static Destination dtoToEntity(DestinationResponse destinationResponse) {
        return new Destination(
                destinationResponse.city(),
                destinationResponse.country(),
                destinationResponse.description(),
                destinationResponse.imageUrl(),
                destinationResponse.username()
        );
    }
}
