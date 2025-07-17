package com.cts.OnlineDeliverySystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String status="Available"; // e.g., "Available", "Inservice"
}
