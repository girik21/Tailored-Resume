package com.resume_tailor.backend.service;

import com.resume_tailor.backend.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    List<Project> getAllByNameContaining(String name);

    Project getProjectById(String projectId);


    Project createProject(Project project);

    Project updateProject(String projectId, Project project);

    void deleteProject(String projectId);
}
