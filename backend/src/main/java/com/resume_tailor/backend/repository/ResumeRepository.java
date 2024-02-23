package com.resume_tailor.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.resume_tailor.backend.model.Resume;

public interface ResumeRepository extends MongoRepository<Resume, Integer> {
    List<Resume> findAllByOrderByCreatedDateDesc();
}
