package com.cts.OnlineDeliverySystem.controller;


import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Get order by customerId
    @GetMapping("/customer/orders/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //Checking if user is admin or not, Returning all orders if yes
    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrders(@RequestHeader("userRole") String userRole) {
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied");
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // To create a new order
    @PostMapping("/customer/createOrder/{amount}/{restaurantId}/{customerId}")
    public ResponseEntity<Long> createOrder(@PathVariable Double amount,
                                              @PathVariable long restaurantId,
                                              @PathVariable long customerId) {
      long orderId =orderService.createOrder(amount, restaurantId, customerId);
        return  ResponseEntity.ok(orderId);
    }

}
