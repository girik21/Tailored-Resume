package com.resume_tailor.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.resume_tailor.backend.model.CoverLetter;

public interface CoverLetterRepository extends MongoRepository<CoverLetter, String> {
}
