package com.hostpilot.dto;

import lombok.Data;

@Data
public class AiRequest {
    private String model;
    private String prompt;
    private boolean stream;

    public AiRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
        this.stream = false;
    }
}
