package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.service.Resume.ResumeService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Resume>>> getUserResumes(@PathVariable String userId) {
        try {
            List<Resume> resumes = resumeService.getUserResumes(userId);
            String successMessage = "Successfully retrieved user's Resumes.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, resumes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{userResumeId}")
    public ResponseEntity<?> getUserResumeById(@PathVariable String userResumeId) {
        try {
            Resume resume = resumeService.getUserResumeById(userResumeId);
            if (resume != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Resume retrieved successfully.", resume));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Resume>> createUserResume(@PathVariable String userId, @Valid @RequestBody Resume resume) {
        try {
            Resume createdResume = resumeService.createUserResume(userId, resume);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User Resume created successfully.", createdResume));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity<ResponseWrapper<Resume>> updateUserResume(@PathVariable String userId, @PathVariable String resumeId, @Valid @RequestBody Resume updatedResume) {
        try {
            Resume resume = resumeService.updateUserResume(userId, resumeId, updatedResume);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Resume updated successfully.", updatedResume));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUserResume(@PathVariable String resumeId) {
        try {
            resumeService.deleteUserResume(resumeId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Resume deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
