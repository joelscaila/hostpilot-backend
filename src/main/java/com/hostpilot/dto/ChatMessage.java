package com.hostpilot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private String role; // "guest" o "agent"
    private String content;
}
