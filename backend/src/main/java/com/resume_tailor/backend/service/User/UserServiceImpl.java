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
    public List<User> getAllUsers(String email) {
        if (email != null) {
            return userRepository.findByEmail(email);
        }
        return userRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserByEmail(email));
        return user.orElse(null);
    }

    @Override
    public User createUser(User user) {
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
