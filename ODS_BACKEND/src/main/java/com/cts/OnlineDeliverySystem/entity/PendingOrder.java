package com.cts.OnlineDeliverySystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PendingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
