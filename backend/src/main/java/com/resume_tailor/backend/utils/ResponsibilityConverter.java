package com.resume_tailor.backend.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume_tailor.backend.dto.Responsibility;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;



public class ResponsibilityConverter {



    public static JsonNode parseToJson(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}