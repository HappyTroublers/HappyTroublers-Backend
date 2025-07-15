package happyTroublers.destination.dtos;

public record DestinationResponse(
        String city,
        String country,
        String description,
        String imageUrl,
        String username
) {
}
