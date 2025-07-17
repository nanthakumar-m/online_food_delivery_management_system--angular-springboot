package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Payment;
import com.cts.OnlineDeliverySystem.exceptions.OrderNotFoundException;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private PaymentService paymentService;



    // Negative Test: Handle payment with non-existent order
    @Test
    public void testHandlePayment_OrderNotFound() {
        // Arrange
        long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(OrderNotFoundException.class, () -> paymentService.handlePayment(orderId));
        assertEquals("Order not Found", exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
        verify(paymentRepository, never()).save(any(Payment.class));
    }



    // Positive Test: Handle successful payment
    @Test
    public void testHandleSuccessPayment_Success() {
        // Arrange
        String stripePaymentIntendId = "mock-intent-id";
        Payment payment = new Payment();
        Order order = new Order();
        payment.setOrder(order);
        when(paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId)).thenReturn(payment);

        // Act
        ResponseEntity<String> response = paymentService.handleSuccesspayment(stripePaymentIntendId);

        // Assert
        assertEquals("Payment success and order placed", response.getBody());
        assertEquals("success", payment.getPaymentStatus());
        assertEquals("Accepted", order.getOrderStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(deliveryService, times(1)).assignAgentToOrder(order);
    }

    // Negative Test: Handle successful payment with invalid Stripe ID
    @Test
    public void testHandleSuccessPayment_InvalidStripeId() {
        // Arrange
        String stripePaymentIntendId = "invalid-id";
        when(paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> paymentService.handleSuccesspayment(stripePaymentIntendId));
        verify(paymentRepository, times(1)).findByStripePaymentIntendId(stripePaymentIntendId);
        verify(paymentRepository, never()).save(any(Payment.class));
        verify(deliveryService, never()).assignAgentToOrder(any(Order.class));
    }

    // Positive Test: Handle payment failure
    @Test
    public void testHandlePaymentFailure_Success() {
        // Arrange
        String stripePaymentIntendId = "mock-intent-id";
        Payment payment = new Payment();
        Order order = new Order();
        payment.setOrder(order);
        when(paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId)).thenReturn(payment);

        // Act
        ResponseEntity<String> response = paymentService.handlePaymentFailiure(stripePaymentIntendId);

        // Assert
        assertEquals("Payment failed", response.getBody());
        assertEquals("failure", payment.getPaymentStatus());
        assertEquals("PaymentPending", order.getOrderStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    // Negative Test: Handle payment failure with invalid Stripe ID
    @Test
    public void testHandlePaymentFailure_InvalidStripeId() {
        // Arrange
        String stripePaymentIntendId = "invalid-id";
        when(paymentRepository.findByStripePaymentIntendId(stripePaymentIntendId)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> paymentService.handlePaymentFailiure(stripePaymentIntendId));
        verify(paymentRepository, times(1)).findByStripePaymentIntendId(stripePaymentIntendId);
        verify(paymentRepository, never()).save(any(Payment.class));
    }
}