package happyTroublers.user.controllers;

import happyTroublers.user.dtos.UserResponse;
import happyTroublers.user.services.CustomUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class CustomUserController {
    private final CustomUserService CUSTOM_USER_SERVICE;

    public CustomUserController(CustomUserService customUserService) {
        this.CUSTOM_USER_SERVICE = customUserService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String username) {
        UserResponse userResponse = CUSTOM_USER_SERVICE.getUserByName(username);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}