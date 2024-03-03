package com.resume_tailor.backend.service.ExperienceProjects;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() { return projectRepository.findAll();}

    @Override
    public List<Project> getAllByNameContaining(String name) { return projectRepository.findByNameContaining(name);}

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    @Override
    public Project getProjectById(String projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        return  project.orElse(null);
    }

    @Override
    public Project updateProject(String projectId, Project updatedProject) {
        updatedProject.setId(projectId);
        return projectRepository.save(updatedProject);
    }

    @Override
    public void deleteProject(String projectId) {projectRepository.deleteById(projectId); }

}
