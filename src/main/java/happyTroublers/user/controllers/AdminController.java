package happyTroublers.user.controllers;

import happyTroublers.user.dtos.AdminRequest;
import happyTroublers.user.dtos.AdminResponse;
import happyTroublers.user.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {
    private final AdminService
    ADMIN_SERVICE;

    public AdminController(AdminService adminService) {
        ADMIN_SERVICE = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllUsers() {
        List<AdminResponse> users = ADMIN_SERVICE.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getUserById(@PathVariable Long id) {
        AdminResponse adminResponse = ADMIN_SERVICE.getUserById(id);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateUser(@PathVariable Long id, @Valid @RequestBody AdminRequest adminRequest) {
        AdminResponse adminResponse = ADMIN_SERVICE.updateUser(id, adminRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        ADMIN_SERVICE.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
