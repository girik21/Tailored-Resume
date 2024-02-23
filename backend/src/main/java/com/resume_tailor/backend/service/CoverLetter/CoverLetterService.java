package com.resume_tailor.backend.service.CoverLetter;

import java.util.List;

import com.resume_tailor.backend.model.CoverLetter;

public interface CoverLetterService {

    List<CoverLetter> getAllCoverLetters();

    CoverLetter getCoverLetterById(Integer coverLetterId);

    CoverLetter createCoverLetter(CoverLetter coverLetter);

    CoverLetter updateCoverLetter(Integer coverLetterId, CoverLetter updatedCoverLetter);

    void deleteCoverLetter(Integer coverLetterId);
}
