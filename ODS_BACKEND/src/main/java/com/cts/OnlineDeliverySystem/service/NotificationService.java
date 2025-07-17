package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Delivery;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Notify customer about the delivery status
    public void notifyCustomer(Delivery delivery, String message) {
        // Simulate sending a notification (e.g., via WebSocket, REST API, or other mechanisms)
//        System.out.println("Notification for Delivery ID " + delivery.getDeliveryId() + ": " + message);
    }
}