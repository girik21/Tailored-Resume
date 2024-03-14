package com.resume_tailor.backend.service.ExperienceProjects;

import com.resume_tailor.backend.model.Experience;
import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.ExperienceRepository;
import com.resume_tailor.backend.repository.ProjectRepository;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public List<Project> getAllProjects() { return projectRepository.findAll();}
    @Override
    public Project createProject(
            String userId,
            String experienceId,
            Project project)
    {
        // Save the project
        Project savedProject = projectRepository.save(project);

        // Associate project with user if userId is provided
        associateProjectWithUser(savedProject, userId);

        // Associate project with experience if experienceId is provided
        associateProjectWithExperience(savedProject, experienceId);

        return savedProject;
    }

    private void associateProjectWithUser(Project project, String userId) {
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                // Associate project with user
                user.addProject(project);
                userRepository.save(user);
            }
        }
    }

    private void associateProjectWithExperience(Project project, String experienceId) {
        if (experienceId != null) {
            Experience experience = experienceRepository.findById(experienceId).orElse(null);
            if (experience != null) {
                // Associate project with experience
                experience.addProject(project);
                experienceRepository.save(experience);
            }
        }
    }

    @Override
    public Project getProjectById(String projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElse(null);
    }

    @Override
    public Project updateProject(String projectId, Project updatedProject) {
        updatedProject.setId(projectId);
        return projectRepository.save(updatedProject);
    }

    @Override
    public void deleteProject(String projectId) {projectRepository.deleteById(projectId); }
}
