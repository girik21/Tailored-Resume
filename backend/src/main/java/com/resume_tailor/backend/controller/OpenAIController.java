package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.dto.ChatRequest;
import com.resume_tailor.backend.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    /**
     * Creates a chat request and sends it to the OpenAI API
     * Returns the first message from the API response
     *
     * @param requestParams containing the jobDescription and sample resume to send to the API
     *
     * @return first message from the API response
     */
    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> requestParams) {
        String jobDesc = requestParams.get("jobDesc");
        String sampleResume = requestParams.get("sampleResume");

        // Define the prompt for OpenAI
        String prompt = String.format("Given the job description:%n%s%n%nGenerate a new resume based on the Harvard resume template from the existing resume:%n%s%n%n", jobDesc, sampleResume);

        ChatRequest request = new ChatRequest(model, prompt);

        ChatResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
