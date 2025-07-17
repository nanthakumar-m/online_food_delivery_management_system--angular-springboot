package com.cts.OnlineDeliverySystem.exceptions;

public class OrderNotFoundException extends  RuntimeException{

     public OrderNotFoundException(String msg){
        super(msg);
    }
}
