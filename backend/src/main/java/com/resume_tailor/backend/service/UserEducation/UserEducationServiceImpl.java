package com.resume_tailor.backend.service.UserEducation;

import com.resume_tailor.backend.model.UserEducation;
import com.resume_tailor.backend.repository.UserEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserEducationServiceImpl implements UserEducationService {

    @Autowired
    private UserEducationRepository userEducationRepository;

    @Override
    public List<UserEducation> getUserEducation(String userId) {
        return userEducationRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public UserEducation getUserEducationById(String userEducationId) {
        Optional<UserEducation> userEducation = userEducationRepository.findById(userEducationId);
        return userEducation.orElse(null);
    }

    @Override
    public UserEducation createUserEducation(String userId, UserEducation userEducation) {
        userEducation.setUserId(userId);
        userEducation.setCreatedDate(new Date());
        return userEducationRepository.save(userEducation);
    }

    @Override
    public UserEducation updateUserEducation(String userId, String educationId, UserEducation updatedEducation) {
        updatedEducation.setId(educationId);
        updatedEducation.setUserId(userId);
        return userEducationRepository.save(updatedEducation);
    }

    @Override
    public void deleteUserEducation(String educationId) {
        userEducationRepository.deleteById(educationId);
    }
}
