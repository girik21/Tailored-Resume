package com.resume_tailor.backend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FirebaseAuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuthenticationService.class);

    public FirebaseToken verifyFirebaseToken(String firebaseIdToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(firebaseIdToken);
        } catch (Exception e) {
            // Log the exception using SLF4J logger
            logger.error("Error verifying Firebase token", e);
            return null;
        }
    }
}

