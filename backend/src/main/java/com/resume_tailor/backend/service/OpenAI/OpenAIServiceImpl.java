package com.resume_tailor.backend.service.OpenAI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{
    @Override
    public void validateRequestParameters(Map<String, String> requestParams){
        if (requestParams == null || requestParams.isEmpty()) {
            throw new IllegalArgumentException("Request parameters cannot be null or empty");
        }

        String jobDesc = requestParams.get("jobDesc");
        String sampleResume = requestParams.get("sampleResume");

        if (jobDesc == null || jobDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("Job description cannot be null or empty");
        }

        if (sampleResume == null || sampleResume.trim().isEmpty()) {
            throw new IllegalArgumentException("Sample resume cannot be null or empty");
        }
    }

    public String createOpenAIPrompt(String jobDesc, String sampleResume){
        return String.format("Given the job description:%n%s%n%n" +
                        "Generate a new resume in json format based on the Harvard resume template from the existing resume:%n%s%n%n" +
                        "Name: <<xxx>>%n" +
                        "Address: <<xxx>>%n" +
                        "Email: <<xxx>>%n" +
                        "Phone: <<xxx-xxx-xxxx>>%n" +
                        "Education: <<xxx>>%n" +
                        "Previous Companies I worked At:%n" +
                        "Name: <<xxx>>%n" +
                        "From - To: <<xxx - xxx>>%n" +
                        "Position: <<Generate relevant position 1 to the Job description>>%n" +
                        "Responsibilities: <<Generate responsibilities relevant to position 1>>%n" +
                        "Name: <<xxx>>%n" +
                        "From - To: <<xxx - xxx>>%n" +
                        "Position: <<Generate relevant position 2 to the Job description>>%n" +
                        "Responsibilities: <<Generate responsibilities relevant to position 2>>%n" +
                        "Name: <<xxx>>%n" +
                        "From - To: <<xxx - xxx>>%n" +
                        "Position: <<Generate relevant position 3 to the Job description>>%n" +
                        "Responsibilities: <<Generate responsibilities relevant to position 3>>%n" +
                        "Skills: <<Generate List of Skills relevant to the positions above>>%n" +
                        "JSON Output Template:%n" +
                        "{%n" +
                        "  \"name\": \"<<Name extracted from resume>>\",%n" +
                        "  \"contact\": {%n" +
                        "    \"LinkedIn\": \"<<LinkedIn extracted from resume>>\",%n" +
                        "    \"phone\": \"<<Phone extracted from resume>>\",%n" +
                        "    \"location\": \"<<Location extracted from resume>>\",%n" +
                        "    \"portfolio\": \"<<Portfolio extracted from resume>>\",%n" +
                        "    \"email\": \"<<Email extracted from resume>>\",%n" +
                        "    \"github\": \"<<GitHub extracted from resume>>\"%n" +
                        "  },%n" +
                        "  \"skills\": {%n" +
                        "    \"programmingLanguages\": [<<Programming Languages extracted from resume>>],%n" +
                        "    \"databases\": [<<Databases extracted from resume>>],%n" +
                        "    \"frameworks\": [<<Frameworks extracted from resume>>],%n" +
                        "    \"otherTechnologies\": [<<Other Technologies extracted from resume>>],%n" +
                        "    \"cloudPlatforms\": [<<Cloud Platforms extracted from resume>>],%n" +
                        "    \"developmentPractices\": [<<Development Practices extracted from resume>>],%n" +
                        "    \"proficiency\": [<<Proficiency extracted from resume>>]%n" +
                        "  },%n" +
                        "  \"workHistory\": [<<Work History extracted from resume>>],%n" +
                        "  \"education\": [<<Education extracted from resume>>],%n" +
                        "  \"projects\": [<<Projects extracted from resume>>],%n" +
                        "  \"mentorship\": {%n" +
                        "    \"role\": \"<<Role extracted from resume>>\",%n" +
                        "    \"responsibilities\": \"<<Responsibilities extracted from resume>>\"%n" +
                        "  }%n" +
                        "}",
                jobDesc, sampleResume);
    }

    public String validateAndFixJson(String jsonString) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Attempt to parse the input string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // If parsing succeeds, the input string is valid JSON
            return jsonString;
        } catch (JsonParseException e) {
            // If parsing fails, the input string is not valid JSON
            System.out.println("Input is not valid JSON. Attempting to fix...");

            // Try to fix the JSON by wrapping it in curly braces
            String fixedJson = "{" + jsonString + "}";

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                // Attempt to parse the fixed string into a JsonNode
                objectMapper.readTree(fixedJson);

                // If parsing succeeds, the fixed string is valid JSON
                System.out.println("JSON successfully fixed.");
                return fixedJson;
            } catch (JsonParseException ex) {
                // If parsing still fails, the input string cannot be fixed
                System.out.println("Unable to fix the JSON. Returning the original input.");
                return jsonString;
            } catch (Exception ex) {
                // Handle other exceptions if necessary
                System.out.println("An unexpected error occurred: " + ex.getMessage());
                return jsonString;
            }
        } catch (Exception e) {
            // Handle other exceptions if necessary
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return jsonString;
        }
    }
}
