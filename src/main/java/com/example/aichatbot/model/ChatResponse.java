package com.example.aichatbot.model;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private List<AnswerBlock> content;
}
