package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
//    List<Project> findByNameContaining(String name);

    List<Project> findByUserId(String userId);
}
