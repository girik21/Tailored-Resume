package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EducationRepository extends MongoRepository<Education, String> {

    List<Education> findByUserIdOrderByStartDateDesc(String userId);
}
