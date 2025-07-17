package com.cts.OnlineDeliverySystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Column(nullable = false)
    private String status; // e.g., "In Progress", "Delivered"

    @Column(name = "estimated_time_of_arrival")
    private LocalDateTime estimatedTimeOfArrival;
}
