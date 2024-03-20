package com.resume_tailor.backend.service.Resume;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.model.Skill;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.ResumeRepository;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Resume> getResumes() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume getResumeById(String userResumeId) {
        Optional<Resume> userResume = resumeRepository.findById(userResumeId);
        return userResume.orElse(null);
    }

    @Override
    public Resume createResume(String userId, Resume resume) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Resume savedResume = resumeRepository.save(resume);

            // Associate with user
            user.addResume(savedResume);
            userRepository.save(user);

            return savedResume;
        }
        return null;
    }

    @Override
    public Resume updateResume(String resumeId, Resume updatedResume) {
        updatedResume.setId(resumeId);
        return resumeRepository.save(updatedResume);
    }

    @Override
    public void deleteResume(String resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}

