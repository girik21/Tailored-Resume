package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Certificate;
import com.resume_tailor.backend.service.Certification.CertificationService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificateController {
    @Autowired
    CertificationService certificationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Certificate>>> getAllCertificates(){
        try{
            List<Certificate> certificates = certificationService.getAllCertificates();
            return ResponseEntity.ok().body(new ResponseWrapper<>(true,"Successfully retrieved all Certificates.", certificates));//<>(Certificates, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<?> getCertificateById(@PathVariable String certificateId) {
        try {
            Certificate certificate = certificationService.getCertificateById(certificateId);
            if (certificate != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Certificate retrieved successfully.", certificate));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
    @PostMapping
    public ResponseEntity<?> createCertificate(
            @RequestParam("userId") String userId,
            @Valid @RequestBody Certificate certificate)
    {
        try {
            Certificate createdCertificate = certificationService.createCertificate(userId, certificate);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "Certificate created successfully.", createdCertificate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<?> updateCertificate(@PathVariable String certificateId, @RequestBody Certificate updatedCertificate) {
        try {
            Certificate certificate = certificationService.updateCertificate(certificateId, updatedCertificate);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Certificate updated successfully.", certificate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<?> deleteCertificate(@PathVariable String certificateId) {
        try {
            certificationService.deleteCertificate(certificateId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Certificate deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
