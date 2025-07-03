package com.example.aichatbot.service;

import com.example.aichatbot.model.AnswerBlock;
import com.example.aichatbot.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openAIApiKey;

    @Value("${openai.model:gpt-4}")
    private String model;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public ChatResponse getAnswer(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String systemPrompt = """
        You are an AI assistant for an enterprise tech company.
        Respond with helpful answers in a JSON-friendly text format.

        If the user's question is about services, products, company offerings, or solutions,
        you should include a special banner by inserting a line in this format:

        [BANNER|Explore Cloud Services|https://yourcompany.com/cloud|https://yourcompany.com/assets/cloud-banner.jpg]

        Place the banner after the first or second sentence of your answer if it makes sense contextually.
        Only include the banner when it’s highly relevant. Do NOT use banners for casual questions or generic tasks.
        """;

        // Construct messages
        List<Map<String, String>> messages = new ArrayList<>();
//        messages.add(Map.of("role", "system", "content", "You are a helpful assistant..."));
        messages.add(Map.of("role", "system", "content", systemPrompt));
        messages.add(Map.of("role", "user", "content", userMessage));
        // Prepare OpenAI API request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 🔥 Call OpenAI API
        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, Map.class);

        // 🧠 Extract content from GPT
        Map messageMap = (Map) ((List) response.getBody().get("choices")).get(0);
        Map innerMessage = (Map) messageMap.get("message");
        String content = (String) innerMessage.get("content");

        // 🎯 Parse into blocks
        List<AnswerBlock> blocks = parseAnswerBlocks(content);

        return new ChatResponse(blocks);
    }

    private List<AnswerBlock> parseAnswerBlocks(String content) {
        List<AnswerBlock> blocks = new ArrayList<>();

        for (String line : content.split("\n")) {
            if (line.contains("[BANNER|")) {
                // Split into before, banner, and after
                int start = line.indexOf("[BANNER|");
                int end = line.indexOf("]", start);

                String before = line.substring(0, start).trim();
                String bannerText = line.substring(start + 8, end); // remove [BANNER| prefix and trailing ]
                String after = line.substring(end + 1).trim();

                // Add text before banner (if any)
                if (!before.isEmpty()) {
                    blocks.add(new AnswerBlock("text", before));
                }

                // Add banner block
                String[] parts = bannerText.split("\\|");
                if (parts.length == 3) {
                    blocks.add(new AnswerBlock("banner", parts[0], parts[1], parts[2]));
                }

                // Add text after banner (if any)
                if (!after.isEmpty()) {
                    blocks.add(new AnswerBlock("text", after));
                }
            } else if (!line.trim().isEmpty()) {
                blocks.add(new AnswerBlock("text", line.trim()));
            }
        }

        return blocks;
    }
}
