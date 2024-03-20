package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, String> {
}
