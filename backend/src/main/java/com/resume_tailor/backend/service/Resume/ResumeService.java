package com.resume_tailor.backend.service.Resume;

import java.util.List;

import com.resume_tailor.backend.model.Resume;

public interface ResumeService {

    List<Resume> getUserResumes(String userId);

    Resume getUserResumeById(String userResumeId);

    Resume createUserResume(String userId, Resume resume);

    Resume updateUserResume(String userId, String resumeId, Resume resume);

    void deleteUserResume(String resumeId);
}
