package happyTroublers.exceptions.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException (String message) {
        super (message);
    }
}
