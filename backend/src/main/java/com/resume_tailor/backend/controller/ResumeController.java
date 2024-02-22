package com.resume_tailor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resume_tailor.backend.model.Resume;
import com.resume_tailor.backend.repository.ResumeRepository;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeRepository resumeRepository;

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @GetMapping("/{resumeId}")
    public Resume getResumeById(@PathVariable Integer resumeId) {
        return resumeRepository.findById(resumeId).orElse(null);
    }

    @PostMapping
    public Resume createResume(@RequestBody Resume resume) {
        return resumeRepository.save(resume);
    }

    @PutMapping("/{resumeId}")
    public Resume updateResume(@PathVariable Integer resumeId, @RequestBody Resume updatedResume) {
        if (resumeRepository.existsById(resumeId)) {
            updatedResume.setId(resumeId);
            return resumeRepository.save(updatedResume);
        }
        return null;
    }

    @DeleteMapping("/{resumeId}")
    public void deleteResume(@PathVariable Integer resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}
