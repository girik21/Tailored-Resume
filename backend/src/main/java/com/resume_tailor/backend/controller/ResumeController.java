package com.resume_tailor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.service.Resume.ResumeService;
import com.resume_tailor.backend.utils.ResponseWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Resume>>> getAllResumes() {
        try {
            List<Resume> resumes = resumeService.getAllResumes();
            String successMessage = "Successfully retrieved all resumes.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, resumes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<?> getResumeById(@PathVariable Integer resumeId) {
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            if (resume != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Resume retrieved successfully.", resume));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createResume(@Valid @RequestBody Resume resume) {
        try {
            Resume createdResume = resumeService.createResume(resume);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(true, "Resume created successfully.", createdResume));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity<?> updateResume(@PathVariable Integer resumeId, @Valid @RequestBody Resume updatedResume) {
        try {
            Resume resume = resumeService.updateResume(resumeId, updatedResume);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Resume updated successfully.", resume));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<?> deleteResume(@PathVariable Integer resumeId) {
        try {
            resumeService.deleteResume(resumeId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Resume deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
