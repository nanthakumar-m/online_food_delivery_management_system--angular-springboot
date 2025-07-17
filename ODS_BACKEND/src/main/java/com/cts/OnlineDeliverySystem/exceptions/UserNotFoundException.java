package com.cts.OnlineDeliverySystem.exceptions;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
