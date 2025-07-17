package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.repository.CustomerRepository;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private OrderService orderService;

    // Positive Test: Create an order successfully
    @Test
    public void testCreateOrder_Success() {
        // Arrange
        double amount = 100.0;
        long restaurantId = 1L;
        long customerId = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);
        restaurant.setOrder(new ArrayList<>()); // Initialize the list

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setOrder(new ArrayList<>()); // Initialize the list

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        long orderId = orderService.createOrder(amount, restaurantId, customerId);

        // Assert
        assertEquals(1L, orderId);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(customerRepository, times(1)).findById(customerId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Negative Test: Create an order with non-existent restaurant
    @Test
    public void testCreateOrder_RestaurantNotFound() {
        // Arrange
        double amount = 100.0;
        long restaurantId = 1L;
        long customerId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(amount, restaurantId, customerId);
        });
        assertEquals("Restaurant not found", exception.getMessage());
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(customerRepository, never()).findById(anyLong());
        verify(orderRepository, never()).save(any(Order.class));
    }

    // Negative Test: Create an order with non-existent customer
    @Test
    public void testCreateOrder_CustomerNotFound() {
        // Arrange
        double amount = 100.0;
        long restaurantId = 1L;
        long customerId = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(amount, restaurantId, customerId);
        });
        assertEquals("Customer not found", exception.getMessage());
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(customerRepository, times(1)).findById(customerId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    // Positive Test: Get orders by customer ID
    @Test
    public void testGetOrdersByCustomerId_Success() {
        // Arrange
        long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order1 = new Order();
        Order order2 = new Order();
        customer.setOrder(Arrays.asList(order1, order2));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);

        // Assert
        assertEquals(2, orders.size());
        verify(customerRepository, times(1)).findById(customerId);
    }

    // Negative Test: Get orders by non-existent customer ID
    @Test
    public void testGetOrdersByCustomerId_CustomerNotFound() {
        // Arrange
        long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.getOrdersByCustomerId(customerId);
        });
        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    // Positive Test: Get all orders
    @Test
    public void testGetAllOrders_Success() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> orders = orderService.getAllOrders();

        // Assert
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }
}