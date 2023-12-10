package com.portfolio.springopenai.controller;

import com.portfolio.springopenai.entity.response.APIDocumentation;
import com.portfolio.springopenai.service.OpenAIService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/")
public class OpenAIController {

    private OpenAIService openAIService;

    @PostMapping(value = "/generate_docs", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<APIDocumentation> generateDocumentation(@RequestParam("curlRequest") String curlRequest) throws BadRequestException {
        APIDocumentation documentation = openAIService.generateDocumentation(curlRequest);
        return ResponseEntity.ok(documentation);
    }
}

