package com.cts.OnlineDeliverySystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String role;
    private String email;
}
