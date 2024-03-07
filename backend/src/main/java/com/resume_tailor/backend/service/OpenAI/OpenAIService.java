package com.resume_tailor.backend.service.OpenAI;

import java.util.Map;

public interface OpenAIService {
    void validateRequestParameters(Map<String, String> requestParams);

    String createOpenAIPrompt(String jobDesc, String sampleResume);

    String validateAndFixJson(String jsonString);
}
