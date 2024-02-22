package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
