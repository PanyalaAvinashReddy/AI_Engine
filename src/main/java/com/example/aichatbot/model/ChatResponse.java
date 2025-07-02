package com.example.aichatbot.model;
import java.util.List;

public class ChatResponse {
    private List<AnswerBlock> content;

    public ChatResponse() {}

    public ChatResponse(List<AnswerBlock> content) {
        this.content = content;
    }

    public List<AnswerBlock> getContent() {
        return content;
    }

    public void setContent(List<AnswerBlock> content) {
        this.content = content;
    }
}
