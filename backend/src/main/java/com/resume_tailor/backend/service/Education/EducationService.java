package com.resume_tailor.backend.service.Education;

import com.resume_tailor.backend.model.Education;

import java.util.List;

public interface EducationService {
    List<Education> getUserEducation(String userId);

    Education getUserEducationById(String userEducationId);

    Education createUserEducation(String userId, Education education);

    Education updateUserEducation(String userId, String educationId, Education education);

    void deleteUserEducation(String educationId);
}
