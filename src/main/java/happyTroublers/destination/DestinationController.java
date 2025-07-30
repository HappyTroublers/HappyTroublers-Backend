package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserDetail;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")

public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponse>> getAllDestinations() {
        List<DestinationResponse> destinations = destinationService.getAllDestinations();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationById(@PathVariable Long id, String username) {
        DestinationResponse destinationResponse = destinationService.getDestinationById(id, username);
        return new ResponseEntity<>(destinationResponse, HttpStatus.OK);
    }

    @GetMapping("/my-destinations")
    public ResponseEntity<List<DestinationResponse>> getDestinationsByUsername(@AuthenticationPrincipal CustomUser user) {
        List<DestinationResponse> destinations = destinationService.getDestinationsByUsername(user.getUsername());
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DestinationResponse> addDestination(@Valid @RequestBody DestinationRequest destinationRequest, @AuthenticationPrincipal CustomUserDetail userDetails) {
        DestinationResponse destinationResponse = destinationService.addDestination(destinationRequest, userDetails.getUsername());
        return new ResponseEntity<>(destinationResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponse> updateDestination(@PathVariable Long id, @Valid @RequestBody DestinationRequest destinationRequest, @AuthenticationPrincipal CustomUserDetail userDetails) {
        DestinationResponse destinationResponse = destinationService.updateDestination(id, destinationRequest, userDetails.getUsername());
        return new ResponseEntity<>(destinationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail userDetails) {
        destinationService.deleteDestination(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DestinationResponse>> filterDestinations(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country
    ) {
        List<DestinationResponse> destinations = destinationService.filterDestinations(city, country);

        if (destinations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }
}