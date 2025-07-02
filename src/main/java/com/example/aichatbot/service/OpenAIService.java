package com.example.aichatbot.service;

import com.example.aichatbot.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${openai.model}")
    private String model;

    public ChatResponse getAnswer(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Build OpenAI Request
        OpenAIRequest request = new OpenAIRequest(
                model,
                List.of(Map.of("role", "user", "content", message)),
                0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<OpenAIRequest> httpEntity = new HttpEntity<>(request, headers);

        // Step 2: Call OpenAI API
        ResponseEntity<OpenAIResponse> response = restTemplate.postForEntity(
                openAiApiUrl,
                httpEntity,
                OpenAIResponse.class
        );

        // Step 3: Convert OpenAI Response to AnswerBlock
        assert response.getBody() != null;
        String reply = response.getBody().getChoices().get(0).getMessage().getContent();

        // For now return one text block; later we’ll parse banners from markdown or tags
        return new ChatResponse(List.of(
                new AnswerBlock("text", reply)
        ));
    }
}
