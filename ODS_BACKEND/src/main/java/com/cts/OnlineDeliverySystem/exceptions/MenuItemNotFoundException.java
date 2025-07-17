package com.cts.OnlineDeliverySystem.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MenuItemNotFoundException extends RuntimeException{
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}

