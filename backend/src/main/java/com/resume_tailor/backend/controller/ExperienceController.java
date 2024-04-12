package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Experience;
import com.resume_tailor.backend.service.Experience.ExperienceService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Experience>>> getExperiences() {
        try {
            List<Experience> experiences = experienceService.getExperiences();
            String successMessage = "Successfully retrieved experiences.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, experiences));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<?> getExperienceById(@PathVariable String experienceId) {
        try {
            Experience experience = experienceService.getExperienceById(experienceId);
            if (experience != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Experience retrieved successfully.", experience));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(false, "Experience not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Experience>> createExperience(@RequestParam("userId") String userId, @Valid @RequestBody Experience experience) {
        try {
            Experience createdExperience = experienceService.createExperience(userId, experience);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User experience created successfully.", createdExperience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<Experience>> updateExperience(@PathVariable String experienceId, @Valid @RequestBody Experience updatedExperience) {
        try {
            Experience experience = experienceService.updateExperience(experienceId, updatedExperience);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience updated successfully.", experience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteExperience(@PathVariable String experienceId) {
        try {
            experienceService.deleteExperience(experienceId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
