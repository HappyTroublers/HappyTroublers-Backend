package happyTroublers.user;

import happyTroublers.destination.dtos.DestinationRequest;
import happyTroublers.user.dtos.AdminRequest;
import happyTroublers.user.dtos.AdminResponse;
import happyTroublers.user.dtos.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CustomUserController {
    private final CustomUserService CUSTOM_USER_SERVICE;

    public CustomUserController(CustomUserService customUserService) {
        this.CUSTOM_USER_SERVICE = customUserService;
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllUsers() {
        List<AdminResponse> users = CUSTOM_USER_SERVICE.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getUserById(@PathVariable Long id) {
       AdminResponse adminResponse = CUSTOM_USER_SERVICE.getUserById(id);
       return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateUser(@PathVariable Long id, @Valid @RequestBody AdminRequest adminRequest) {
        AdminResponse adminResponse = CUSTOM_USER_SERVICE.updateUser(id, adminRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        CUSTOM_USER_SERVICE.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
