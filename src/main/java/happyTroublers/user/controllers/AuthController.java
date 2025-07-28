package happyTroublers.user.controllers;

import happyTroublers.user.dtos.UserRequest;
import happyTroublers.user.dtos.UserResponse;
import happyTroublers.user.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AdminService ADMIN_SERVICE;

    public AuthController(AdminService adminService) {
        ADMIN_SERVICE = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest userRequest) {
         return new ResponseEntity<>(ADMIN_SERVICE.addUser(userRequest), HttpStatus.CREATED);
    }
}
