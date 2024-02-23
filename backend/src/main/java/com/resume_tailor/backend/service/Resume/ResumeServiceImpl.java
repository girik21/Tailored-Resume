package com.resume_tailor.backend.service.Resume;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.repository.ResumeRepository;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume getResumeById(Integer resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        return resume.orElse(null);
    }

    @Override
    public Resume createResume(Resume resume) {
        resume.setCreatedAt(new Date());
        return resumeRepository.save(resume);
    }

    @Override
    public Resume updateResume(Integer resumeId, Resume updatedResume) {
        updatedResume.setId(resumeId);
        return resumeRepository.save(updatedResume);
    }

    @Override
    public void deleteResume(Integer resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}
