package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.UserEducation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserEducationRepository extends MongoRepository<UserEducation, String> {

    List<UserEducation> findByUserIdOrderByStartDateDesc(String userId);
}
