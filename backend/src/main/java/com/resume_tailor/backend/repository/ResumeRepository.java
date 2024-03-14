package com.resume_tailor.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.resume_tailor.backend.model.Resume;

import java.util.List;

public interface ResumeRepository extends MongoRepository<Resume, String> {
}
