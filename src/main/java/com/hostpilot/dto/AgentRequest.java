package com.hostpilot.dto;

import lombok.Data;

@Data
public class AgentRequest {
    private Long propertyId;
    private String message;
}
