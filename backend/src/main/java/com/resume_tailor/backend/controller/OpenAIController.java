package com.resume_tailor.backend.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume_tailor.backend.dto.ChatRequest;
import com.resume_tailor.backend.dto.ChatResponse;
import com.resume_tailor.backend.service.OpenAI.OpenAIService;
import com.resume_tailor.backend.service.User.UserService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private UserService userService;

    /**
     * Creates a chat request and sends it to the OpenAI API
     * Returns the first message from the API response
     *
     * @param requestBody containing the jobDescription and sample resume to send to the API
     *
     * @return first message from the API response
     */
    @PostMapping("/chat/{userId}")
    public ResponseEntity<?> chat(@PathVariable String userId, @RequestBody Map<String, String> requestBody) {
        try {
            var user = userService.getUserById(userId);
            //openAIService.validateRequestParameters(requestParams);
            String jobDesc = requestBody.get("jobDesc");
            String sampleResume = openAIService.generateEscapedResume(user); //requestParams.get("sampleResume");
            String prompt = openAIService.createOpenAIPrompt(jobDesc, sampleResume);

            ChatRequest request = new ChatRequest(model, prompt);

            ChatResponse response = restTemplate.postForObject(
                    apiUrl,
                    request,
                    ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String jsonString = response.getChoices().get(0).getMessage().getContent();

            ObjectMapper objectMapper = new ObjectMapper();

            String validatedString = openAIService.validateAndFixJson(jsonString);

            // Convert the JSON-formatted string to a JsonNode (JSON object)
            JsonNode jsonNode = objectMapper.readTree(validatedString);

            // Return the JsonNode
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "Enhanced resume generated successfully.", jsonNode));

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
