package com.resume_tailor.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.resume_tailor.backend.model.Resume;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, Integer> {
}
