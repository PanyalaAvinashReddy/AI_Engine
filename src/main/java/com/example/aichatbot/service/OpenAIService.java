package com.example.aichatbot.service;
import com.example.aichatbot.model.AnswerBlock;
import com.example.aichatbot.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {
    public ChatResponse getAnswer(String message) {
        return new ChatResponse(List.of(
                new AnswerBlock("text", "Yes! We provide cloud consulting services."),
                new AnswerBlock("banner", "Explore Cloud Services", "https://yourcompany.com/cloud", "https://yourcompany.com/img/cloud.jpg"),
                new AnswerBlock("text", "Want to schedule a free call?")
        ));
    }
}
