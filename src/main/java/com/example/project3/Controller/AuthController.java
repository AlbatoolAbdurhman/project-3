package com.example.project3.Controller;

import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {


    private final AuthService authService;

    //for Employe
    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//    //public endpoint
//    @PostMapping("/register")
//    public ResponseEntity registerUser(@RequestBody @Valid User user) {
//        authService.registerUser(user);
//        return ResponseEntity.ok("User registered successfully");
//    }

    //authenticated user
    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid User updatedUser) {
        authService.updateUser(user.getId(), updatedUser);
        return ResponseEntity.ok("User updated successfully");
    }

    //for Employe
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        authService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
