package happyTroublers.destination.dtos;
import happyTroublers.destination.Destination;
import happyTroublers.user.CustomUser;

public class DestinationMapper {
    public static Destination dtoToEntity(DestinationRequest dto, CustomUser user) {
        Destination destination = new Destination();
        destination.setCity(dto.city());
        destination.setCountry(dto.country());
        destination.setDescription(dto.description());
        destination.setImageUrl(dto.imageUrl());
        destination.setUser(user);

        return destination;
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
