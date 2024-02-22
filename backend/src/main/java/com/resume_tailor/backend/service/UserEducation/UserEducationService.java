package com.resume_tailor.backend.service.UserEducation;

import com.resume_tailor.backend.model.UserEducation;

import java.util.List;

public interface UserEducationService {
    List<UserEducation> getUserEducation(String userId);

    UserEducation getUserEducationById(String userEducationId);

    UserEducation createUserEducation(String userId, UserEducation userEducation);

    UserEducation updateUserEducation(String userId, String educationId, UserEducation userEducation);

    void deleteUserEducation(String educationId);
}
