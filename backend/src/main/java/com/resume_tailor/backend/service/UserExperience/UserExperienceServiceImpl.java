package com.resume_tailor.backend.service.UserExperience;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.UserExperience;
import com.resume_tailor.backend.repository.ProjectRepository;
import com.resume_tailor.backend.repository.UserExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserExperienceServiceImpl implements UserExperienceService {

    @Autowired
    private UserExperienceRepository userExperienceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<UserExperience> getUserExperiences(String userId) {
        return userExperienceRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public UserExperience getUserExperienceById(String userExperienceId) {
        Optional<UserExperience> userExperience = userExperienceRepository.findById(userExperienceId);
        return userExperience.orElse(null);
    }

    @Override
    public UserExperience createUserExperience(String userId, UserExperience userExperience) {
        userExperience.setUserId(userId);
        userExperience.setCreatedDate(new Date());
        return userExperienceRepository.save(userExperience);
    }

    @Override
    public UserExperience updateUserExperience(String userId, String experienceId, UserExperience updatedExperience) {
        updatedExperience.setId(experienceId);
        updatedExperience.setUserId(userId);
        return userExperienceRepository.save(updatedExperience);
    }

    @Override
    public void deleteUserExperience(String experienceId) {
        userExperienceRepository.deleteById(experienceId);
    }

    public List<Project> getProjectsByExperienceId(String experienceId) {
        Optional<UserExperience> experienceOptional = userExperienceRepository.findById(experienceId);
        return experienceOptional.map(UserExperience::getProjects).orElse(null);
    }

    public Project addProjectToExperience(String experienceId, Project project) {
        Optional<UserExperience> experienceOptional = userExperienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            UserExperience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            projects.add(project);
            experience.setProjects(projects);
            userExperienceRepository.save(experience);
            return project;
        }
        return null;
    }

    public Project updateProjectInExperience(String experienceId, String projectId, Project project) {
        Optional<UserExperience> experienceOptional = userExperienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            UserExperience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            for (int i = 0; i < projects.size(); i++) {
                if (projects.get(i).getId().equals(projectId)) {
                    project.setId(projectId);
                    projects.set(i, project);
                    experience.setProjects(projects);
                    userExperienceRepository.save(experience);
                    return project;
                }
            }
        }
        return null;
    }

    public void deleteProjectFromExperience(String experienceId, String projectId) {
        Optional<UserExperience> experienceOptional = userExperienceRepository.findById(experienceId);
        if (experienceOptional.isPresent()) {
            UserExperience experience = experienceOptional.get();
            List<Project> projects = experience.getProjects();
            projects.removeIf(project -> project.getId().equals(projectId));
            experience.setProjects(projects);
            userExperienceRepository.save(experience);
        }
    }
}
