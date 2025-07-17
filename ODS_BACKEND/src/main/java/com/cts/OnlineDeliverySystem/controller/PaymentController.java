package com.cts.OnlineDeliverySystem.controller;



import com.cts.OnlineDeliverySystem.exceptions.OrderNotFoundException;
import com.cts.OnlineDeliverySystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;


    @GetMapping("/payments/pay/{orderId}")
    public String handlePayment(@PathVariable int orderId) throws OrderNotFoundException {
        return paymentService.handlePayment(orderId);
    }

    @GetMapping("/payments/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("payment_intent_id") String stripePaymentIntendId){
        return paymentService.handleSuccesspayment(stripePaymentIntendId);
    }

    @GetMapping("payments/cancel")
    public ResponseEntity<String> paymentFailure(@RequestParam("payment_intent_id") String stripePaymentIntendId){
        return paymentService.handlePaymentFailiure(stripePaymentIntendId);
    }
}