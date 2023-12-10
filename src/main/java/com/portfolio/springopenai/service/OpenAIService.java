package com.portfolio.springopenai.service;

import com.google.gson.Gson;
import com.portfolio.springopenai.entity.openai.OpenAIRequest;
import com.portfolio.springopenai.entity.openai.OpenAIResponse;
import com.portfolio.springopenai.entity.response.APIDocumentation;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.url}")
    private String openaiUrl;

    public APIDocumentation generateDocumentation(String curlRequest) throws BadRequestException {
        // Create OpenAIRequest object
        OpenAIRequest request = new OpenAIRequest();
        Gson gson = new Gson();

        request.setModel("gpt-3.5-turbo");

        OpenAIRequest.Message message = new OpenAIRequest.Message();
        message.setRole("user");
        message.setContent(createPromptWithCurl(curlRequest));

        request.setMessages(List.of(message));
        request.setTemperature(0.7);

        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Create HTTP entity with headers and payload
        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        // Make HTTP request to OpenAI API
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<OpenAIResponse> response = restTemplate.postForEntity(openaiUrl, entity, OpenAIResponse.class);

            String content = Objects.requireNonNull(response.getBody()).getChoices().get(0).getMessage().getContent();

            return gson.fromJson(content, APIDocumentation.class);

        } catch (HttpClientErrorException ex) {
//            Gson gson = new Gson();
            String errorMessage = ex.getMessage();

            System.out.println(errorMessage);

            throw new BadRequestException(errorMessage);

        }
    }

    // Private method to format content
    private String createPromptWithCurl(String curlRequest) {
        return "Given this CURL command: " + curlRequest + ", create a text documentation for an API as a json but in a string with the following as its keys:\n" +
                "title,endpoint,authorization,method,response";
    }

}
