package com.resume_tailor.backend.service.OpenAI;

import com.resume_tailor.backend.model.User;

import java.util.Map;

public interface OpenAIService {
    void validateRequestParameters(Map<String, String> requestParams);

    String createOpenAIPrompt(String jobDesc, String sampleResume);

    String validateAndFixJson(String jsonString);

    String generateEscapedResume(User user);
}
