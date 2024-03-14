package com.resume_tailor.backend.service.ExperienceProjects;

import com.resume_tailor.backend.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project getProjectById(String projectId);

    Project createProject(String userId, String experienceId, Project project);

    Project updateProject(String projectId, Project project);

    void deleteProject(String projectId);
}
