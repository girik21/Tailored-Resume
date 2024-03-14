package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.service.ExperienceProjects.ProjectService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Project>>> getAllProjects(){
        try{
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok().body(new ResponseWrapper<>(true,"Successfully retrieved all projects.", projects));//<>(projects, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        try {
            Project project = projectService.getProjectById(projectId);
            if (project != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project retrieved successfully.", project));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestParam("userId") String userId,
            @RequestParam(value = "experienceId", required = false) String experienceId,
            @Valid @RequestBody Project project)
    {
        try {
            Project createdProject = projectService.createProject(userId, experienceId, project);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "Project created successfully.", createdProject));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody Project updatedProject) {
        try {
            Project project = projectService.updateProject(projectId, updatedProject);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project updated successfully.", project));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
