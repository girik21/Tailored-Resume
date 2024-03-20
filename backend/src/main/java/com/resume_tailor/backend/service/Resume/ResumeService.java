package com.resume_tailor.backend.service.Resume;

import java.util.List;

import com.resume_tailor.backend.model.Resume;

public interface ResumeService {

    List<Resume> getResumes();

    Resume getResumeById(String resumeId);

    Resume createResume(String userId, Resume resume);

    Resume updateResume(String resumeId, Resume resume);

    void deleteResume(String resumeId);
}
