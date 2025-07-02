package com.example.aichatbot.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerBlock {
    private String type;
    private String value;
    private String link;
    private String image;

    public AnswerBlock(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
