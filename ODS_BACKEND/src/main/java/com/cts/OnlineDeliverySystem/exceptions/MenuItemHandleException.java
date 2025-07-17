package com.cts.OnlineDeliverySystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MenuItemHandleException extends RuntimeException {

    public MenuItemHandleException(String message){

        super(message);

    }
}
