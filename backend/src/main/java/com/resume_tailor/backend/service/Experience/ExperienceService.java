package com.resume_tailor.backend.service.Experience;

import com.resume_tailor.backend.model.Project;
import com.resume_tailor.backend.model.Experience;

import java.util.List;

public interface ExperienceService {
    List<Experience> getExperiences();

    Experience getExperienceById(String experienceId);

    Experience createExperience(String userId, Experience experience);

    Experience updateExperience(String experienceId, Experience experience);

    void deleteExperience(String experienceId);
}
