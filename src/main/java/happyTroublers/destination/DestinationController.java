package happyTroublers.destination;

import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.destination.dtos.DestinationResponse;
import happyTroublers.user.CustomUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/destinations")

public class DestinationController {
    private final DestinationService DESTINATION_SERVICE;

    public DestinationController(DestinationService destinationService) {
        DESTINATION_SERVICE = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponse>> getAllDestinations(){
        List<DestinationResponse> destinations = DESTINATION_SERVICE.getAllDestinations();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationById(@PathVariable Long id) {
        DestinationResponse destinationResponse = DESTINATION_SERVICE.getDestinationById(id);
        return new ResponseEntity<>(destinationResponse, HttpStatus.OK);
    }

    @GetMapping("/{user}")
    public ResponseEntity<List<DestinationResponse>> getDestinationsByUser(@PathVariable CustomUser user) {
        List<DestinationResponse> destinations = DESTINATION_SERVICE.getDestinationsByUser(user);
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<DestinationResponse> addDestination(@Valid @RequestBody DestinationRequest destinationRequest) {
        DestinationResponse destinationResponse = DESTINATION_SERVICE.addDestination(destinationRequest);
        return new ResponseEntity<>(destinationResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponse> updateDestination(@PathVariable Long id, @Valid @RequestBody DestinationRequest destinationRequest) {
        DestinationResponse destinationResponse = DESTINATION_SERVICE.updateDestination(id, destinationRequest);
            return new ResponseEntity<>(destinationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id){
        DESTINATION_SERVICE.deleteDestination(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}