package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationMapper;
import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.exceptions.custom_exceptions.DestinationNotFoundException;
import happyTroublers.exceptions.custom_exceptions.UserNotFoundException;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository DESTINATION_REPOSITORY;
    private final CustomUserRepository CUSTOM_USER_REPOSITORY;

    public DestinationService(DestinationRepository destinationRepository, CustomUserRepository customUserRepository) {
        this.DESTINATION_REPOSITORY = destinationRepository;
        this.CUSTOM_USER_REPOSITORY = customUserRepository;
    }

    public List<DestinationResponse> getAllDestinations() {
        List<Destination> destinations = DESTINATION_REPOSITORY.findAll();
        return destinations.stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public DestinationResponse getDestinationById(Long id) {
        Destination destination = DESTINATION_REPOSITORY.findById(id).orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));
        return DestinationMapper.entityToDto(destination);
    }

    public List<DestinationResponse> getDestinationsByUsername(String username) {
        CustomUser user = CUSTOM_USER_REPOSITORY.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));
        List<Destination> destinations = DESTINATION_REPOSITORY.findByUser(user).orElseThrow(() -> new DestinationNotFoundException("Destination with username " + user + " not found"));
        return destinations.stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public DestinationResponse addDestination(DestinationRequest destinationRequest) {
        CustomUser foundUser = CUSTOM_USER_REPOSITORY.findByUsername(destinationRequest.username()).orElseThrow(() -> new UserNotFoundException("User " + destinationRequest.username() + " not found"));
        Destination newDestination = DestinationMapper.dtoToEntity(destinationRequest, foundUser);
        Destination savedDestination = DESTINATION_REPOSITORY.save(newDestination);
        return DestinationMapper.entityToDto(savedDestination);
    }

    public DestinationResponse updateDestination(Long id, DestinationRequest destinationRequest) {
        Destination existingDestination = DESTINATION_REPOSITORY.findById(id).orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));
        existingDestination.setCity(destinationRequest.city());
        existingDestination.setCountry(destinationRequest.country());
        existingDestination.setDescription(destinationRequest.description());
        existingDestination.setImageUrl(destinationRequest.imageUrl());
        Destination updatedDestination = DESTINATION_REPOSITORY.save(existingDestination);
        return DestinationMapper.entityToDto(updatedDestination);
    }

    public void deleteDestination(Long id) {
        DestinationResponse destination = getDestinationById(id);
        DESTINATION_REPOSITORY.deleteById(id);
    }

    public List<DestinationResponse> filterDestinations(String city, String country) {
        if (city != null && !city.isBlank()) {
            return filterByCity(city);
        }

        if (country != null && !country.isBlank()) {
            return filterByCountry(country);
        }

        return getAllDestinations();
    }

    public List<DestinationResponse> filterByCity(String city) {
        return DESTINATION_REPOSITORY.findByCityIgnoreCase(city).stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public List<DestinationResponse> filterByCountry(String country) {
        return DESTINATION_REPOSITORY.findByCountryIgnoreCase(country).stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }
}