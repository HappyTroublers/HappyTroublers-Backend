package happyTroublers.user.controllers;

import happyTroublers.user.dtos.AdminRequest;
import happyTroublers.user.dtos.AdminResponse;
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
    public ResponseEntity<AdminResponse> addUser(@RequestBody @Valid AdminRequest adminRequest) {
         return new ResponseEntity<>(ADMIN_SERVICE.addUser(adminRequest), HttpStatus.CREATED);
    }
}
