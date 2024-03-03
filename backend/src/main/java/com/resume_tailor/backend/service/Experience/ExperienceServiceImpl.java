package com.resume_tailor.backend.service.Experience;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.Experience;
import com.resume_tailor.backend.repository.ProjectRepository;
import com.resume_tailor.backend.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Experience> getUserExperiences(String userId) {
        return experienceRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public Experience getUserExperienceById(String userExperienceId) {
        Optional<Experience> userExperience = experienceRepository.findById(userExperienceId);
        return userExperience.orElse(null);
    }

    @Override
    public Experience createUserExperience(String userId, Experience experience) {
        experience.setUserId(userId);
        experience.setCreatedDate(new Date());
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateUserExperience(String userId, String experienceId, Experience updatedExperience) {
        updatedExperience.setId(experienceId);
        updatedExperience.setUserId(userId);
        return experienceRepository.save(updatedExperience);
    }

    @Override
    public void deleteUserExperience(String experienceId) {
        experienceRepository.deleteById(experienceId);
    }

    public List<Project> getProjectsByExperienceId(String experienceId) {
        Optional<Experience> experienceOptional = experienceRepository.findById(experienceId);
        return experienceOptional.map(Experience::getProjects).orElse(null);
    }

    public Project addProjectToExperience(String experienceId, Project project) {
        Optional<Experience> experienceOptional = experienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            Experience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            projects.add(project);
            experience.setProjects(projects);
            experienceRepository.save(experience);
            return project;
        }
        return null;
    }

    public Project updateProjectInExperience(String experienceId, String projectId, Project project) {
        Optional<Experience> experienceOptional = experienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            Experience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            for (int i = 0; i < projects.size(); i++) {
                if (projects.get(i).getId().equals(projectId)) {
                    project.setId(projectId);
                    projects.set(i, project);
                    experience.setProjects(projects);
                    experienceRepository.save(experience);
                    return project;
                }
            }
        }
        return null;
    }

    public void deleteProjectFromExperience(String experienceId, String projectId) {
        Optional<Experience> experienceOptional = experienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            Experience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            projects.removeIf(project -> project.getId().equals(projectId));
            experience.setProjects(projects);
            experienceRepository.save(experience);
        }
    }
}
