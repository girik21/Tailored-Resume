package com.resume_tailor.backend.controller;

import com.mongodb.DuplicateKeyException;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.security.Role;
import com.resume_tailor.backend.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home() {
        return "Your API works!!";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + result.getFieldError().getDefaultMessage());
        }

        // Create a new user
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(Role.USER); // Set default role

        // Save user to MongoDB
        try {
            userService.createUser(user);
            return ResponseEntity.ok("User registered successfully!!");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
    }
}
