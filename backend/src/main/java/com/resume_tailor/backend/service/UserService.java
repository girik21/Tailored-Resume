package com.resume_tailor.backend.service;

import com.resume_tailor.backend.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(String userId);

    User createUser(User user);

    User updateUser(String userId, User user);

    void deleteUser(String userId);
}