package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Payment;
import com.cts.OnlineDeliverySystem.exceptions.OrderNotFoundException;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class PaymentService {


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Value("${stripe.api.key}")
    private String stripeApiKey;



    public String handlePayment(long orderId) throws OrderNotFoundException{

        Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not Found"));
        double amount=order.getTotalAmount();
        long amountInPaise =Math.round(amount*100);
        Stripe.apiKey = stripeApiKey;


        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/payment-success?payment_intent_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:8085/customer/payments/cancel?payment_intent_id={CHECKOUT_SESSION_ID}")
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(

                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("INR")
                                            .setUnitAmount(amountInPaise)
                                            .setProductData(

                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Order payment")
                                                            .build())
                                            .build())
                            .build())
                    .build();
            Session session = Session.create(params);

            //        save the payment Entity
            Payment payment = new Payment();

            payment.setTotalAmount(amount);
            payment.setPaymentMethod("card");
            payment.setPaymentStatus("pending");
            payment.setStripePaymentIntendId(session.getId());
            payment.setOrder(order);

            order.setPayment(payment);
            paymentRepository.save(payment);
            orderRepository.save(order);
            return session.getUrl();

        } catch (StripeException e) {
            return "Error occurred while creating payment session: " + e.getMessage();
        }
    }

    //changing the payment status after payment
    public ResponseEntity<String> handleSuccesspayment(String stripePaymentIntendId) {
        Payment payment =paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId);
        payment.setPaymentStatus("success");
        payment.getOrder().setOrderStatus("Accepted");
        payment.getOrder().setOrderAcceptedTime(LocalDateTime.now());
        paymentRepository.save(payment);

//        call the assignAgentOrder to start the delivery
        deliveryService.assignAgentToOrder(payment.getOrder());
        return new ResponseEntity<>("Payment success and order placed", HttpStatus.CREATED);
    }

    public ResponseEntity<String > handlePaymentFailiure(String stripePaymentIntendId) {
        Payment payment =paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId);
        payment.setPaymentStatus("failure");
        payment.getOrder().setOrderStatus("PaymentPending");
        paymentRepository.save(payment);
        return new ResponseEntity<>("Payment failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
