package com.resume_tailor.backend.service.Education;

import com.resume_tailor.backend.model.Education;
import com.resume_tailor.backend.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Override
    public List<Education> getUserEducation(String userId) {
        return educationRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public Education getUserEducationById(String userEducationId) {
        Optional<Education> userEducation = educationRepository.findById(userEducationId);
        return userEducation.orElse(null);
    }

    @Override
    public Education createUserEducation(String userId, Education education) {
        education.setUserId(userId);
        education.setCreatedDate(new Date());
        return educationRepository.save(education);
    }

    @Override
    public Education updateUserEducation(String userId, String educationId, Education updatedEducation) {
        updatedEducation.setId(educationId);
        updatedEducation.setUserId(userId);
        return educationRepository.save(updatedEducation);
    }

    @Override
    public void deleteUserEducation(String educationId) {
        educationRepository.deleteById(educationId);
    }
}
