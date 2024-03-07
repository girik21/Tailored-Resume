package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.service.Education.EducationService;
import com.resume_tailor.backend.service.Experience.ExperienceService;
import com.resume_tailor.backend.service.ExperienceProjects.ProjectService;
import com.resume_tailor.backend.service.Skill.SkillService;
import com.resume_tailor.backend.service.User.UserService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<User>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            String successMessage = "Successfully retrieved all users.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            User user = userService.getUserWithDetails(userId);
            if (user != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User retrieved successfully.", user));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User created successfully.", createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @Valid @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User updated successfully.", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}