package com.portfolio.springopenai.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContentParser {
    public JsonNode parseContentString(String contentString) {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] sections = contentString.split("\n\n");

        // Create a JSON object to store the extracted information
        ObjectNode jsonNode = objectMapper.createObjectNode();

        for (String section : sections) {
            String[] lines = section.split("\n");
            if (lines.length >= 2) {
                String key = lines[0].trim();
                String value = section.substring(lines[0].length()).trim();

                // Process Request Body separately to handle multiline content
                if (key.equals("Request Body:")) {
                    value = value.replaceAll("```json", "").replaceAll("```", "").trim();
                }

                // Explicitly handle title, method, and response
                if (key.equals("Title:") || key.equals("Method:") || key.equals("Response:")) {
                    // Add the key-value pair to the JSON object
                    jsonNode.put(key, value);
                } else {
                    // For other sections, add the key-value pair to the JSON object
                    jsonNode.put(key, value);
                }
            }
        }

        return jsonNode;
    }
}

