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

import com.resume_tailor.backend.model.CoverLetter;
import com.resume_tailor.backend.repository.CoverLetterRepository;

@RestController
@RequestMapping("/api/coverletters")
public class CoverLetterController {

    @Autowired
    private CoverLetterRepository coverLetterRepository;

    @GetMapping
    public List<CoverLetter> getAllCoverLetters() {
        return (List<CoverLetter>) coverLetterRepository.findAll();
    }

    @GetMapping("/{coverLetterId}")
    public CoverLetter getCoverLetterById(@PathVariable Integer coverLetterId) {
        return coverLetterRepository.findById(coverLetterId).orElse(null);
    }

    @PostMapping
    public CoverLetter createCoverLetter(@RequestBody CoverLetter coverLetter) {
        return coverLetterRepository.save(coverLetter);
    }

    @PutMapping("/{coverLetterId}")
    public CoverLetter updateCoverLetter(@PathVariable Integer coverLetterId, @RequestBody CoverLetter updatedCoverLetter) {
        if (coverLetterRepository.existsById(coverLetterId)) {
            updatedCoverLetter.setId(coverLetterId);
            return coverLetterRepository.save(updatedCoverLetter);
        }
        return null;
    }

    @DeleteMapping("/{coverLetterId}")
    public void deleteCoverLetter(@PathVariable Integer coverLetterId) {
        coverLetterRepository.deleteById(coverLetterId);
    }
}
