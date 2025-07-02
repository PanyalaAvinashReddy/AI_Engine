package com.example.aichatbot.controller;

import com.example.aichatbot.model.ChatRequest;
import com.example.aichatbot.model.ChatResponse;
import com.example.aichatbot.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*") // Important for frontend
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(openAIService.getAnswer(request.getMessage()));
    }
}