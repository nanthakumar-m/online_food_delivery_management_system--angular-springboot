package com.cts.OnlineDeliverySystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MenuItemSaveException extends RuntimeException {
    public MenuItemSaveException(String message) {
        super(message);
    }
}
