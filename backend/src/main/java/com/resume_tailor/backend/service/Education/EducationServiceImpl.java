package com.resume_tailor.backend.service.Education;

import com.resume_tailor.backend.model.Education;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.EducationRepository;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Education> getEducation() {
        return educationRepository.findAll();
    }

    @Override
    public Education getEducationById(String educationId) {
        Optional<Education> education = educationRepository.findById(educationId);
        return education.orElse(null);
    }

    @Override
    public Education createEducation(String userId, Education education) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            education.setCreatedDate(new Date());
            Education savedEducation = educationRepository.save(education);

            // Associate with the user
            user.addEducation(savedEducation);
            userRepository.save(user);

            return savedEducation;
        }
        return null;
    }

    @Override
    public Education updateEducation(String educationId, Education updatedEducation) {
        updatedEducation.setId(educationId);
        return educationRepository.save(updatedEducation);
    }

    @Override
    public void deleteEducation(String educationId) {
        educationRepository.deleteById(educationId);
    }
}
