package com.resume_tailor.backend.service;

import com.resume_tailor.backend.model.UserExperience;

import java.util.List;

public interface UserExperienceService {
    List<UserExperience> getUserExperiences(String userId);

    UserExperience getUserExperienceById(String userExperienceId);

    UserExperience createUserExperience(String userId, UserExperience userExperience);

    UserExperience updateUserExperience(String userId, String experienceId, UserExperience userExperience);

    void deleteUserExperience(String experienceId);
}
