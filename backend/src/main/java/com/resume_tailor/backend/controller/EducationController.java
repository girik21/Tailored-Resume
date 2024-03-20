package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Education;
import com.resume_tailor.backend.service.Education.EducationService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {
    @Autowired
    private EducationService educationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Education>>> getEducation() {
        try {
            List<Education> educations = educationService.getEducation();
            String successMessage = "Successfully retrieved education.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, educations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{educationId}")
    public ResponseEntity<?> getEducationById(@PathVariable String educationId) {
        try {
            Education education = educationService.getEducationById(educationId);
            if (education != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Education retrieved successfully.", education));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Education>> createEducation(@RequestParam("userId") String userId, @Valid @RequestBody Education education) {
        try {
            Education createdEducation = educationService.createEducation(userId, education);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User education created successfully.", createdEducation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<ResponseWrapper<Education>> updateEducation(@PathVariable String educationId, @Valid @RequestBody Education updatedEducation) {
        try {
            Education education = educationService.updateEducation(educationId, updatedEducation);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User education updated successfully.", education));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteEducation(@PathVariable String educationId) {
        try {
            educationService.deleteEducation(educationId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User education deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
