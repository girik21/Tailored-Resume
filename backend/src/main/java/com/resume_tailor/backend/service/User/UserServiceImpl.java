package com.resume_tailor.backend.service.User;

import com.resume_tailor.backend.model.*;
import com.resume_tailor.backend.repository.UserRepository;
import com.resume_tailor.backend.service.Education.EducationService;
import com.resume_tailor.backend.service.Experience.ExperienceService;
import com.resume_tailor.backend.service.ExperienceProjects.ProjectService;
import com.resume_tailor.backend.service.Skill.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public User getUserWithDetails(String userId) {
        User user = getUserById(userId);
        if (user != null) {
            List<Experience> experiences = experienceService.getUserExperiences(userId);
            if (experiences != null) {
                user.setExperiences(experiences);
            }

            List<Education> education = educationService.getUserEducation(userId);
            if (education != null) {
                user.setEducation(education);
            }

            List<Project> projects = projectService.getUserProjects(userId);
            if (projects != null) {
                user.setProjects(projects);
            }

            List<Skill> skills = skillService.getUserSkills(userId);
            if (skills != null) {
                user.setSkills(skills);
            }
        }
        return user;
    }


    @Override
    public User createUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        updatedUser.setId(userId);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
