package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.service.UserExperienceProjects.ProjectService;
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

    @GetMapping("/projects")
    public String project() {
        return "Testing projects";
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Project>>> getAllProjects(@RequestParam(required = false) String name){
        try{
            List<Project> projects = new ArrayList<Project>();

            if(name == null)
                projects = projectService.getAllProjects();
            else
                projects = projectService.getAllByNameContaining(name);

            if(projects.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

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
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
        try {
            Project createdProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "Project created successfully.", createdProject));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody Project updatedProject) {
        try {
            Project user = projectService.updateProject(projectId, updatedProject);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Project updated successfully.", user));
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
