package com.resume_tailor.backend.service.OpenAI;

import com.fasterxml.jackson.databind.JsonNode;
import com.resume_tailor.backend.dto.Responsibility;
import com.resume_tailor.backend.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface OpenAIService {
    void validateRequestParameters(Map<String, String> requestParams);

    String createOpenAIPrompt(String jobDesc, String sampleResume);

    String createOpenAIExperiencePrompt(String jobPosition, String company);

    String validateAndFixJson(String jsonString);

    String generateEscapedResume(User user);

    JsonNode generateExperienceResponsibilities(String jobDescription, String company);

    String extractString(JsonNode input);
}
