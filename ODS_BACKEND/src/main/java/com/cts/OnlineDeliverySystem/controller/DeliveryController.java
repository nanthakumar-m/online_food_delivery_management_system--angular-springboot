package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.entity.Delivery;
import com.cts.OnlineDeliverySystem.exceptions.DeliveryNotFoundException;
import com.cts.OnlineDeliverySystem.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")

public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    // Endpoint to track delivery status by orderId
    @GetMapping("/track-delivery/{orderId}")
    public ResponseEntity<Map<String, String>> trackDelivery(@PathVariable Long orderId) throws DeliveryNotFoundException {
        Delivery delivery = deliveryRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found for Order ID: " + orderId));
        Map<String, String> response = new HashMap<>();
        response.put("orderId", String.valueOf(orderId));
        response.put("status", delivery.getStatus());
        return ResponseEntity.ok(response);
    }
}