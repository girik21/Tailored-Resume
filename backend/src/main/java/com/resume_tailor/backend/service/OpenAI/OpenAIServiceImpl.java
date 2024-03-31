package com.resume_tailor.backend.service.OpenAI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.resume_tailor.backend.dto.ChatRequest;
import com.resume_tailor.backend.dto.ChatResponse;
import com.resume_tailor.backend.dto.Responsibility;
import com.resume_tailor.backend.model.Education;
import com.resume_tailor.backend.model.Experience;
import com.resume_tailor.backend.model.Skill;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.resume_tailor.backend.utils.ResponsibilityConverter.parseToJson;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
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

    @Override
    public String createOpenAIPrompt(String jobDesc, String sampleResume){
        return  String.format("{%n" +
                        "  \"jobDescription\": \"%s\",%n" +
                        "  \"sampleResume\": \"%s\",%n" +
                        "  \"resumeTemplate\": {%n" +
                        "    \"name\": \"<<Name extracted from resume>>\",%n" +
                        "    \"contact\": {%n" +
                        "      \"LinkedIn\": \"<<LinkedIn extracted from resume>>\",%n" +
                        "      \"phone\": \"<<Phone extracted from resume>>\",%n" +
                        "      \"location\": \"<<Location extracted from resume>>\",%n" +
                        "      \"portfolio\": \"<<Portfolio extracted from resume>>\",%n" +
                        "      \"email\": \"<<Email extracted from resume>>\",%n" +
                        "      \"github\": \"<<GitHub extracted from resume>>\"%n" +
                        "    },%n" +
                        "    \"skills\": {%n" +
                        "      \"programmingLanguages\": [<<Programming Languages extracted from resume>>],%n" +
                        "      \"databases\": [<<Databases extracted from resume>>],%n" +
                        "      \"frameworks\": [<<Frameworks extracted from resume>>],%n" +
                        "      \"otherTechnologies\": [<<Other Technologies extracted from resume>>],%n" +
                        "      \"cloudPlatforms\": [<<Cloud Platforms extracted from resume>>],%n" +
                        "      \"developmentPractices\": [<<Development Practices extracted from resume>>],%n" +
                        "      \"proficiency\": [<<Proficiency extracted from resume>>]%n" +
                        "    },%n" +
                        "    \"workHistory\": [<<Work History extracted from resume>>],%n" +
                        "    \"education\": [<<Education extracted from resume>>],%n" +
                        "    \"projects\": [<<Projects extracted from resume>>],%n" +
                        "    \"mentorship\": {%n" +
                        "      \"role\": \"<<Role extracted from resume>>\",%n" +
                        "      \"responsibilities\": \"<<Responsibilities extracted from resume>>\"%n" +
                        "    }%n" +
                        "  }%n" +
                        "}",
                jobDesc, sampleResume);
    }

    @Override
    public String createOpenAIExperiencePrompt(String jobPosition, String company){
        // Structure the Prompt
        // Structure the Prompt
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Generate list of responsibilities relevant to a ")
                .append(jobPosition)
                .append(" at ")
                .append(company)
                .append(". Return 5 responses formatted into JSON. ")
                .append("Just return the responses in the following format:  ")
                .append("[\n" +
                        "    {\n" +
                        "      \"responsibility\": \"responsibility 1.\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"responsibility\": \"responsibility 2.\"\n" +
                        "    }   \n" +
                        "]");

        return promptBuilder.toString();
    }

    @Override
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
    @Override
    public String generateEscapedResume(User user) {
        StringBuilder resume = new StringBuilder();

        resume.append("\nHere is the existing Resume: \n\n")
                .append(user.getUsername())
                .append("\n")
                .append(user.getAddress1())
                .append(user.getAddress2() != null ? ", " + user.getAddress2() : "")
                .append("\n")
                .append(user.getCity())
                .append(", ")
                .append(user.getState())
                .append(", ")
                .append(user.getZip())
                .append("\n")
                .append(user.getEmail())
                .append("\n")
                .append(user.getPhone())
                .append("\n\nEducation:\n");

        List<Education> educationList = user.getEducation();
        if (educationList != null) {
            for (Education education : educationList) {
                resume.append(education.getMajor())
                        .append("\n")
                        .append(education.getSchool())
                        .append(", ")
                        .append(education.getLocation())
                        .append("\n")
                        .append(dateFormat.format(education.getStartDate()))
                        .append(" - ")
                        .append(dateFormat.format(education.getEndDate()))
                        .append("\n\n");
            }
        }

        resume.append("\nPrevious Companies I worked At: \n");

        List<Experience> experienceList = user.getExperiences();
        if (experienceList != null) {
            for (Experience experience : experienceList) {
                resume.append("Name: ")
                        .append(experience.getEmployer())
                        .append("\nFrom - To: ")
                        .append(dateFormat.format(experience.getStartDate()))
                        .append(" - ")
                        .append(dateFormat.format(experience.getEndDate()))
                        .append("\nPosition: ")
                        .append(experience.getPosition())
                        .append("\nResponsibilities: ")
                        .append(experience.getDescription())
                        .append("\n\n");
            }
        }

        resume.append("Skills:\n");

        List<Skill> skills = user.getSkills();
        if (skills != null) {
            for (int i = 0; i < skills.size(); i++) {
                resume.append(skills.get(i).getName());
                if (i < skills.size() - 1) {
                    resume.append(", ");
                }
            }
        }

        return resume.toString();
    }

    @Override
    public JsonNode generateExperienceResponsibilities( String jobDescription, String company) {
        try {



            String prompt = createOpenAIExperiencePrompt(jobDescription, company);

            ChatRequest request = new ChatRequest(model, prompt);

            ChatResponse response = restTemplate.postForObject(
                    apiUrl,
                    request,
                    ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return null;
            }
            String jsonString = response.getChoices().get(0).getMessage().getContent();

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode =  parseToJson(jsonString); //objectMapper.readTree(jsonString);

//            ArrayList<Responsibility> responsibilities = new ArrayList<>();
//            // If the root element is an object
//            if (jsonNode.isObject()) {
//                System.out.println("Iterating through object:");
//                jsonNode.fields().forEachRemaining(entry -> {
//                    Responsibility responsibility = new Responsibility();
//                    responsibility.setDescription(entry.getValue().toString());
//                    responsibilities.add(responsibility);//System.out.println(entry.getKey() + ": " + entry.getValue());
//                });
//            }
//
//            // If the root element is an array
//            if (jsonNode.isArray()) {
//                System.out.println("Iterating through array:");
//                jsonNode.elements().forEachRemaining(element -> {
//                    Responsibility responsibility = new Responsibility();
//                    responsibility.setDescription(extractString(element));
//                    responsibilities.add(responsibility);
//                });
//            }

            //Return the json object
            return jsonNode;// parseToJson(jsonString);


        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return null;
        }

    }

    public  String extractString(JsonNode node) {
        // Extract the value associated with the "responsibility" key
        JsonNode responsibilityNode = node.get("responsibility");

        // Return the extracted value as a string
        if (responsibilityNode != null) {
            return responsibilityNode.asText();
        }

        // Return null if extraction fails
        return null;
    }

}
