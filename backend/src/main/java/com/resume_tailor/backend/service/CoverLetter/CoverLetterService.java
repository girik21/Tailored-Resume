package com.resume_tailor.backend.service.CoverLetter;

import java.util.List;

import com.resume_tailor.backend.model.CoverLetter;

public interface CoverLetterService {

    List<CoverLetter> getAllCoverLetters();

    CoverLetter getCoverLetterById(String coverLetterId);

    CoverLetter createCoverLetter(String userId, CoverLetter coverLetter);

    CoverLetter updateCoverLetter(String coverLetterId, CoverLetter updatedCoverLetter);

    void deleteCoverLetter(String coverLetterId);
}
