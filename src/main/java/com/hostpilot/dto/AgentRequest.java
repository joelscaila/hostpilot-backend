package com.hostpilot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hostId;
    private String name;
    private String wifi;
    private String checkIn;
    private String checkOut;
    private String rules;
    private String location;
    private String welcomeMessage;
}
