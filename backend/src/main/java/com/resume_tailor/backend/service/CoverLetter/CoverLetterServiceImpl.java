package com.resume_tailor.backend.service.CoverLetter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume_tailor.backend.model.CoverLetter;
import com.resume_tailor.backend.repository.CoverLetterRepository;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

    @Autowired
    private CoverLetterRepository coverLetterRepository;

    @Override
    public List<CoverLetter> getAllCoverLetters() {
        return coverLetterRepository.findAll();
    }

    @Override
    public CoverLetter getCoverLetterById(Integer coverLetterId) {
        Optional<CoverLetter> coverLetter = coverLetterRepository.findById(coverLetterId);
        return coverLetter.orElse(null);
    }

    @Override
    public CoverLetter createCoverLetter(CoverLetter coverLetter) {
        coverLetter.setCreatedAt(new Date());
        return coverLetterRepository.save(coverLetter);
    }

    @Override
    public CoverLetter updateCoverLetter(Integer coverLetterId, CoverLetter updatedCoverLetter) {
        updatedCoverLetter.setId(coverLetterId);
        return coverLetterRepository.save(updatedCoverLetter);
    }

    @Override
    public void deleteCoverLetter(Integer coverLetterId) {
        coverLetterRepository.deleteById(coverLetterId);
    }
}
