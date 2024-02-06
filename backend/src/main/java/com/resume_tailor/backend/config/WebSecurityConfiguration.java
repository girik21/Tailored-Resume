package com.resume_tailor.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/**").authenticated() // Require authentication for all endpoints
                .anyRequest().authenticated() // Require authentication for any other endpoint
                .and()
                .oauth2ResourceServer()
                .jwt(); // Configure JWT token authentication

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Configure Firebase public key URI
        String firebasePublicKeyUri = "https://www.googleapis.com/robot/v1/metadata/x509/test-app-133d0.apps.googleusercontent.com";

        // Create JwtDecoder using NimbusJwtDecoder with Firebase public key URI
        return NimbusJwtDecoder.withJwkSetUri(firebasePublicKeyUri).build();
    }
}
