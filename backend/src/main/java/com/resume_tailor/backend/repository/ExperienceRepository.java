package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Experience;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExperienceRepository extends MongoRepository<Experience, String> {

    List<Experience> findByUserIdOrderByStartDateDesc(String userId);
}
