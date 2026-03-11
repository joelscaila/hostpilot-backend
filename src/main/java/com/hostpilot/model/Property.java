package com.hostpilot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "properties")
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String checkIn;

    private String checkOut;

    private String wifiName;

    private String wifiPassword;

    @Column(length = 2000)
    private String rules;

    @Column(length = 5000)
    private String description;
}
