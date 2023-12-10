package com.portfolio.springopenai.entity.response;

import lombok.Data;

@Data
public class APIDocumentation {
    private String title;
    private String endpoint;
    private String authorization;
//    private String headers;
    private String method;
    private String requestBody;
//    private String response;
}
