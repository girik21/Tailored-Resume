package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}