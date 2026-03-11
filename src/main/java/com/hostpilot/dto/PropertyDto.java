package com.hostpilot.dto;

import lombok.Data;

@Data
public class PropertyDto {
    private Long id;
    private String name;
    private String address;
    private String checkIn;
    private String checkOut;
    private String wifiName;
    private String wifiPassword;
    private String rules;
    private String description;
}
