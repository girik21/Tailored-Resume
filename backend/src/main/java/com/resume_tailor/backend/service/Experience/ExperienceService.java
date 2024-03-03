package com.resume_tailor.backend.service.Experience;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.Experience;

import java.util.List;

public interface ExperienceService {
    List<Experience> getUserExperiences(String userId);

    Experience getUserExperienceById(String userExperienceId);

    Experience createUserExperience(String userId, Experience experience);

    Experience updateUserExperience(String userId, String experienceId, Experience experience);

    void deleteUserExperience(String experienceId);

    public List<Project> getProjectsByExperienceId(String experienceId);

    public Project addProjectToExperience(String experienceId, Project project);

    public Project updateProjectInExperience(String experienceId, String projectId, Project project);

    public void deleteProjectFromExperience(String experienceId, String projectId);


}
