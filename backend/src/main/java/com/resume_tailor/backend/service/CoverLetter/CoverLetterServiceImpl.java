package com.resume_tailor.backend.service.CoverLetter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume_tailor.backend.model.CoverLetter;
import com.resume_tailor.backend.repository.CoverLetterRepository;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

    @Autowired
    private CoverLetterRepository coverLetterRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CoverLetter> getAllCoverLetters() {
        return coverLetterRepository.findAll();
    }

    @Override
    public CoverLetter getCoverLetterById(String coverLetterId) {
        Optional<CoverLetter> coverLetter = coverLetterRepository.findById(coverLetterId);
        return coverLetter.orElse(null);
    }

    @Override
    public CoverLetter createCoverLetter(String userId, CoverLetter coverLetter) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            coverLetter.setCreatedAt(new Date());
            CoverLetter savedCoverLetter = coverLetterRepository.save(coverLetter);

            // Associate with the user
            user.addCoverLetter(coverLetter);
            userRepository.save(user);

            return savedCoverLetter;
        }
        return null;
    }

    @Override
    public CoverLetter updateCoverLetter(String coverLetterId, CoverLetter updatedCoverLetter) {
        updatedCoverLetter.setId(coverLetterId);
        return coverLetterRepository.save(updatedCoverLetter);
    }

    @Override
    public void deleteCoverLetter(String coverLetterId) {
        coverLetterRepository.deleteById(coverLetterId);
    }
}
