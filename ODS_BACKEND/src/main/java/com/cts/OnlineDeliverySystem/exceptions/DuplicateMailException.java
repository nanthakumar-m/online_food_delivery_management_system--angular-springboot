package com.cts.OnlineDeliverySystem.exceptions;

public class DuplicateMailException extends  RuntimeException{
    public DuplicateMailException(String message) {
        super(message);
    }
}
