package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.UserEducation;
import com.resume_tailor.backend.service.UserEducationService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/education")
public class UserEducationController {
    @Autowired
    private UserEducationService userEducationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<UserEducation>>> getUserEducations(@PathVariable String userId) {
        try {
            List<UserEducation> educations = userEducationService.getUserEducation(userId);
            String successMessage = "Successfully retrieved user's education.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, educations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{educationId}")
    public ResponseEntity<?> getUserEducationById(@PathVariable String educationId) {
        try {
            UserEducation userEducation = userEducationService.getUserEducationById(educationId);
            if (userEducation != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Education retrieved successfully.", userEducation));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserEducation>> createUserEducation(@PathVariable String userId, @Valid @RequestBody UserEducation userEducation) {
        try {
            UserEducation createdEducation = userEducationService.createUserEducation(userId, userEducation);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User education created successfully.", createdEducation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<ResponseWrapper<UserEducation>> updateUserEducation(@PathVariable String userId, @PathVariable String educationId, @Valid @RequestBody UserEducation updatedEducation) {
        try {
            UserEducation education = userEducationService.updateUserEducation(userId, educationId, updatedEducation);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User education updated successfully.", education));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUserEducation(@PathVariable String educationId) {
        try {
            userEducationService.deleteUserEducation(educationId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User education deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
