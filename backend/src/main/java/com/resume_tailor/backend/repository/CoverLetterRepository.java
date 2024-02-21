package com.resume_tailor.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.resume_tailor.backend.model.CoverLetter;

@Repository
public interface CoverLetterRepository extends CrudRepository<CoverLetter, Integer> {
}
