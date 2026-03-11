package com.hostpilot.dto;

import lombok.Data;

@Data
public class AgentResponse {
    private String model;
    private String created_at;
    private String response;
    private boolean done;
}
