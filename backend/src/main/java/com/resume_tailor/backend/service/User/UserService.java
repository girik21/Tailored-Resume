package com.resume_tailor.backend.service.User;

import com.resume_tailor.backend.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers(String email);

    User getUserById(String userId);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(String userId, User user);

    void deleteUser(String userId);
}