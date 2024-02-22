package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.UserExperience;
import com.resume_tailor.backend.service.UserExperience.UserExperienceService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/experiences")
public class UserExperienceController {
    @Autowired
    private UserExperienceService userExperienceService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<UserExperience>>> getUserExperiences(@PathVariable String userId) {
        try {
            List<UserExperience> experiences = userExperienceService.getUserExperiences(userId);
            String successMessage = "Successfully retrieved user experiences.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, experiences));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<?> getUserExperienceById(@PathVariable String experienceId) {
        try {
            UserExperience userExperience = userExperienceService.getUserExperienceById(experienceId);
            if (userExperience != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Experience retrieved successfully.", userExperience));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserExperience>> createUserExperience(@PathVariable String userId, @Valid @RequestBody UserExperience userExperience) {
        try {
            UserExperience createdExperience = userExperienceService.createUserExperience(userId, userExperience);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User experience created successfully.", createdExperience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<UserExperience>> updateUserExperience(@PathVariable String userId, @PathVariable String experienceId, @Valid @RequestBody UserExperience updatedExperience) {
        try {
            UserExperience experience = userExperienceService.updateUserExperience(userId, experienceId, updatedExperience);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience updated successfully.", experience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUserExperience(@PathVariable String experienceId) {
        try {
            userExperienceService.deleteUserExperience(experienceId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
