package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByOrderByCreatedDateDesc();
    List<User> findByEmail(String email);
    User getUserByEmail(String email);
}