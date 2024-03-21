package com.resume_tailor.backend.repository;

import com.resume_tailor.backend.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
}
