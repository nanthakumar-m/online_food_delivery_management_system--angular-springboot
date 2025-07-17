package com.cts.OnlineDeliverySystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "`order`")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String OrderStatus="not confirmed"; // e.g., "Pending", "Accepted", "Preparing", "Delivered"

    @Column(nullable = false)
    private Double totalAmount;

    @Column(name = "order_accepted_time")
    private LocalDateTime orderAcceptedTime; // Timestamp when the order was accepted

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "paymentId")
    private Payment payment;
}

