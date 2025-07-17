package com.cts.OnlineDeliverySystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private String paymentMethod; // e.g., "Card", "Wallet", etc.

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String PaymentStatus; // e.g., "Successful", "Failed"

    //   storing the stripe payment id for changing the payment status in future
    private String stripePaymentIntendId;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "order_id" ,nullable = true)
    private Order order;
}
