package com.resume_tailor.backend.service.Resume;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public List<Resume> getUserResumes(String userId) {
        return resumeRepository.findByUserId(userId);
    }

    @Override
    public Resume getUserResumeById(String userResumeId) {
        Optional<Resume> userResume = resumeRepository.findById(userResumeId);
        return userResume.orElse(null);
    }

    @Override
    public Resume createUserResume(String userId, Resume resume) {
        resume.setUserId(userId);
        resume.setCreatedAt(new Date());
        return resumeRepository.save(resume);
    }

    @Override
    public Resume updateUserResume(String userId, String resumeId, Resume updatedResume) {
        updatedResume.setId(resumeId);
        updatedResume.setUserId(userId);
        return resumeRepository.save(updatedResume);
    }

    @Override
    public void deleteUserResume(String resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}

