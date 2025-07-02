package com.example.aichatbot.controller;

import com.example.aichatbot.model.ChatRequest;
import com.example.aichatbot.model.ChatResponse;
import com.example.aichatbot.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*") // Important for frontend
@Tag(name = "AI Chat", description = "Ask questions and get answers with banners")
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @Operation(summary = "Ask the AI assistant", description = "Sends a message and receives an AI-generated response with banners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful answer"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(openAIService.getAnswer(request.getMessage()));
    }
}