package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.UserExperience;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserExperienceRepository extends MongoRepository<UserExperience, String> {

    List<UserExperience> findByUserIdOrderByStartDateDesc(String userId);
}
