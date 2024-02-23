package com.resume_tailor.backend.service.Resume;

import java.util.List;

import com.resume_tailor.backend.model.Resume;

public interface ResumeService {

    List<Resume> getAllResumes();

    Resume getResumeById(Integer resumeId);

    Resume createResume(Resume resume);

    Resume updateResume(Integer resumeId, Resume updatedResume);

    void deleteResume(Integer resumeId);
}
