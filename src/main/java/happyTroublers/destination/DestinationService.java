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

    private final DestinationRepository destinationRepository;
    private final CustomUserRepository customUserRepository;

    public DestinationService(DestinationRepository destinationRepository, CustomUserRepository customUserRepository) {
        this.destinationRepository = destinationRepository;
        this.customUserRepository = customUserRepository;
    }

    public List<DestinationResponse> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();

        return destinations.stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public DestinationResponse getDestinationById(Long id, String username) {
        Destination destination = destinationRepository.findById(id).orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));

        return DestinationMapper.entityToDto(destination);
    }

    public List<DestinationResponse> getDestinationsByUsername(String username) {
        CustomUser user = customUserRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));
        List<Destination> destinations = destinationRepository.findByUser(user);

        if (destinations.isEmpty()) {
            throw new DestinationNotFoundException("Destination with username " + user + " not found");
        }

        return destinations.stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public DestinationResponse addDestination(DestinationRequest destinationRequest, String username) {
        CustomUser foundUser = customUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));

        Destination newDestination = DestinationMapper.dtoToEntity(destinationRequest, foundUser);
        Destination savedDestination = destinationRepository.save(newDestination);

        return DestinationMapper.entityToDto(savedDestination);
    }


        public DestinationResponse updateDestination(Long id, DestinationRequest destinationRequest, String username) {

            List<Destination> destinationsByUsername = destinationRepository.findByUserUsername(username);

            Destination existingDestination = destinationsByUsername.stream().filter(destination -> destination.getId().equals(id)).findFirst().orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));


       /* Destination existingDestination = destinationRepository.findByIdAndUserUsername(id, username).orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));

       if (!existingDestination.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this destination");
       }*/

        existingDestination.setCity(destinationRequest.city());
        existingDestination.setCountry(destinationRequest.country());
        existingDestination.setDescription(destinationRequest.description());
        existingDestination.setImageUrl(destinationRequest.imageUrl());

        Destination updatedDestination = destinationRepository.save(existingDestination);
        return DestinationMapper.entityToDto(updatedDestination);
    }

    public void deleteDestination(Long id, String username) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException("Destination with id " + id + " not found"));

       /* if (!destination.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this destination");
        }*/

        destinationRepository.delete(destination);
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
        return destinationRepository.findByCityIgnoreCase(city).stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }

    public List<DestinationResponse> filterByCountry(String country) {
        return destinationRepository.findByCountryIgnoreCase(country).stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
    }
}