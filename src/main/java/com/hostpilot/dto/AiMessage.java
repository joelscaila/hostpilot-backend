package com.hostpilot.dto;

import lombok.Data;

@Data
public class AgentResponse {
    private String model;
    private String created_at;
    private String response;   // Ollama puts the final text here
    private AiMessage message; // fallback for other providers
    private boolean done;
}
