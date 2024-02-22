package com.resume_tailor.backend.service;

import com.resume_tailor.backend.model.UserExperience;
import com.resume_tailor.backend.repository.UserExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserExperienceServiceImpl implements UserExperienceService{

    @Autowired
    private UserExperienceRepository userExperienceRepository;

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
}
