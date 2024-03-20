package com.resume_tailor.backend.service.Experience;

import com.resume_tailor.backend.model.Experience;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.ProjectRepository;
import com.resume_tailor.backend.repository.ExperienceRepository;
import com.resume_tailor.backend.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Experience> getExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(String userExperienceId) {
        Optional<Experience> userExperience = experienceRepository.findById(userExperienceId);
        return userExperience.orElse(null);
    }

    @Override
    public Experience createExperience(String userId, Experience experience) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            experience.setCreatedDate(new Date());
            Experience savedExperience = experienceRepository.save(experience);

            // Associate with the user
            user.addExperience(experience);
            userRepository.save(user);

            return savedExperience;
        }
        return null;
    }

    @Override
    public Experience updateExperience(String experienceId, Experience updatedExperience) {
        // Since you're using DBRef, you don't need to update the userId
        updatedExperience.setId(experienceId);
        return experienceRepository.save(updatedExperience);
    }


    @Override
    public void deleteExperience(String experienceId) {
        experienceRepository.deleteById(experienceId);
    }

}
