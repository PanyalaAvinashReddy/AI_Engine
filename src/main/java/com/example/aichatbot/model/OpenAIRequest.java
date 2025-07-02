package com.example.aichatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class OpenAIRequest {
    private String model;
    private List<Map<String, String>> messages;
    private double temperature;
}
