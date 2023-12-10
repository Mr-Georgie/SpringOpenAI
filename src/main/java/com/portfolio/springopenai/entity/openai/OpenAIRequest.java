package com.portfolio.springopenai.entity.openai;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIRequest {

    private String model;
    private List<Message> messages;
    private double temperature;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
