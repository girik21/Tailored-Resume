package com.resume_tailor.backend.service.Education;

import com.resume_tailor.backend.model.Education;

import java.util.List;

public interface EducationService {
    List<Education> getEducation();

    Education getEducationById(String educationId);

    Education createEducation(String userId, Education education);

    Education updateEducation(String educationId, Education education);

    void deleteEducation(String educationId);
}
