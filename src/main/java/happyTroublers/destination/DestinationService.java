package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationMapper;
import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Destination destination = DESTINATION_REPOSITORY.findById(id).orElseThrow(() -> new EntityNotFoundException("Destination with id " + id + " not found"));
        return DestinationMapper.entityToDto(destination);
    }

    public DestinationResponse addDestination(DestinationRequest destinationRequest) {
        CustomUser foundUser = CUSTOM_USER_REPOSITORY.findByUsername(destinationRequest.user()).orElseThrow(() -> new EntityNotFoundException("User " + username + " not found") );
        Destination newDestination = DestinationMapper.dtoToEntity(destinationRequest, foundUser);
        Destination savedDestination = DESTINATION_REPOSITORY.save(newDestination);
        return DestinationMapper.entityToDto(savedDestination);
    }

}
