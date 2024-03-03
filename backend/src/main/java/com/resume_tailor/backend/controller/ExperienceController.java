package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Project;
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
@RequestMapping("/api/users/{userId}/experiences")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Experience>>> getUserExperiences(@PathVariable String userId) {
        try {
            List<Experience> experiences = experienceService.getUserExperiences(userId);
            String successMessage = "Successfully retrieved user experiences.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, experiences));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<?> getUserExperienceById(@PathVariable String experienceId) {
        try {
            Experience experience = experienceService.getUserExperienceById(experienceId);
            if (experience != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Experience retrieved successfully.", experience));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Experience>> createUserExperience(@PathVariable String userId, @Valid @RequestBody Experience experience) {
        try {
            Experience createdExperience = experienceService.createUserExperience(userId, experience);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User experience created successfully.", createdExperience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<Experience>> updateUserExperience(@PathVariable String userId, @PathVariable String experienceId, @Valid @RequestBody Experience updatedExperience) {
        try {
            Experience experience = experienceService.updateUserExperience(userId, experienceId, updatedExperience);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience updated successfully.", experience));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUserExperience(@PathVariable String experienceId) {
        try {
            experienceService.deleteUserExperience(experienceId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User experience deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{experienceId}/projects")
    public ResponseEntity<?> getProjectsByExperienceId( @PathVariable String experienceId) {
        try {
            List<Project> projects = experienceService.getProjectsByExperienceId( experienceId);
            if (projects != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Projects retrieved successfully.", projects));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{experienceId}/projects")
    public ResponseEntity<?> addProjectToExperience( @PathVariable String experienceId, @Valid @RequestBody Project project) {
        try {
            Project savedProject = experienceService.addProjectToExperience( experienceId, project);
            if (savedProject != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "Project added to experience successfully.", savedProject));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{experienceId}/projects/{projectId}")
    public ResponseEntity<?> updateProjectInExperience( @PathVariable String experienceId, @PathVariable String projectId, @Valid @RequestBody Project project) {
        try {
            Project updatedProject = experienceService.updateProjectInExperience( experienceId, projectId, project);
            if (updatedProject != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project updated successfully.", updatedProject));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{experienceId}/projects/{projectId}")
    public ResponseEntity<?> deleteProjectFromExperience( @PathVariable String experienceId, @PathVariable String projectId) {
        try {
            experienceService.deleteProjectFromExperience(experienceId, projectId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }


}
