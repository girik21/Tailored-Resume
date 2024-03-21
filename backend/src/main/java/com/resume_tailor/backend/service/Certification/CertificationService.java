package com.resume_tailor.backend.service.Certification;

import com.resume_tailor.backend.model.Certificate;

import java.util.List;

public interface CertificationService {
    List<Certificate> getAllCertificates();

    Certificate getCertificateById(String certificateId);

    Certificate createCertificate(String userId, Certificate certificate);

    Certificate updateCertificate(String certificateId, Certificate certificate);

    void deleteCertificate(String certificateId);
}
