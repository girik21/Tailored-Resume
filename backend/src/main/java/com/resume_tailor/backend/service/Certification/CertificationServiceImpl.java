package com.resume_tailor.backend.service.Certification;

import com.resume_tailor.backend.model.Certificate;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.CertificateRepository;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Certificate> getAllCertificates() { return certificateRepository.findAll();}
    @Override
    public Certificate createCertificate(
            String userId,
            Certificate certificate)
    {
        // Save the Certificate
        Certificate savedCertificate = certificateRepository.save(certificate);

        // Associate Certificate with user if userId is provided
        associateCertificateWithUser(savedCertificate, userId);

        return savedCertificate;
    }

    private void associateCertificateWithUser(Certificate certificate, String userId) {
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                // Associate Certificate with user
                user.addCertificate(certificate);
                userRepository.save(user);
            }
        }
    }

    @Override
    public Certificate getCertificateById(String certificateId){
        Optional<Certificate> certificate = certificateRepository.findById(certificateId);
        return certificate.orElse(null);
    }

    @Override
    public Certificate updateCertificate(String certificateId, Certificate updatedCertificate) {
        updatedCertificate.setId(certificateId);
        return certificateRepository.save(updatedCertificate);
    }

    @Override
    public void deleteCertificate(String certificateId) {certificateRepository.deleteById(certificateId); }
}
