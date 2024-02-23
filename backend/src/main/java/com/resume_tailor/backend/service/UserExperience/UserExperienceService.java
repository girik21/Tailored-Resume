package com.resume_tailor.backend.service.UserExperience;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.UserExperience;

import java.util.List;

public interface UserExperienceService {
    List<UserExperience> getUserExperiences(String userId);

    UserExperience getUserExperienceById(String userExperienceId);

    UserExperience createUserExperience(String userId, UserExperience userExperience);

    UserExperience updateUserExperience(String userId, String experienceId, UserExperience userExperience);

    void deleteUserExperience(String experienceId);

    public List<Project> getProjectsByExperienceId(String experienceId);

    public Project addProjectToExperience(String experienceId, Project project);

    public Project updateProjectInExperience(String experienceId, String projectId, Project project);

    public void deleteProjectFromExperience(String experienceId, String projectId);


}