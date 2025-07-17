package com.cts.OnlineDeliverySystem.dto;


import lombok.Data;

@Data
public class LoginResponse {

    private String jwtToken;
    private long userId;
}
