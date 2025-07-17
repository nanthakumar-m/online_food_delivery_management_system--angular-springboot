package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Agent;
import com.cts.OnlineDeliverySystem.entity.Delivery;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.PendingOrder;
import com.cts.OnlineDeliverySystem.repository.AgentRepository;
import com.cts.OnlineDeliverySystem.repository.DeliveryRepository;
import com.cts.OnlineDeliverySystem.repository.PendingOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private PendingOrderRepository pendingOrderRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private DeliveryService deliveryService;

    // Positive Test: Assign agent to order
    @Test
    public void testAssignAgentToOrder_Success() {
        // Arrange
        Order order = new Order();
        Agent agent = new Agent();
        agent.setAgentId(1L);
        agent.setName("John Doe");
        agent.setStatus("Available");

        when(agentRepository.findFirstByStatus("Available")).thenReturn(Optional.of(agent));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(new Delivery());

        // Act
        String result = deliveryService.assignAgentToOrder(order);

        // Assert
        assertEquals("Order assigned to agent: John Doe", result);
        verify(agentRepository, times(1)).findFirstByStatus("Available");
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
        verify(agentRepository, times(1)).save(agent);
    }

    // Negative Test: No available agents
    @Test
    public void testAssignAgentToOrder_NoAvailableAgents() {
        // Arrange
        Order order = new Order();
        when(agentRepository.findFirstByStatus("Available")).thenReturn(Optional.empty());

        // Act
        String result = deliveryService.assignAgentToOrder(order);

        // Assert
        assertEquals("No available agents. Order has been queued for assignment.", result);
        verify(pendingOrderRepository, times(1)).save(any(PendingOrder.class));
    }

    // Positive Test: Process queued orders
    @Test
    public void testProcessQueuedOrders_Success() {
        // Arrange
        PendingOrder pendingOrder = new PendingOrder();
        Order order = new Order();
        pendingOrder.setOrder(order);

        Agent agent = new Agent();
        agent.setAgentId(1L);
        agent.setStatus("Available");

        when(pendingOrderRepository.findAll()).thenReturn(List.of(pendingOrder));
        when(agentRepository.findFirstByStatus("Available")).thenReturn(Optional.of(agent));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(new Delivery());

        // Act
        deliveryService.processQueuedOrders();

        // Assert
        verify(pendingOrderRepository, times(1)).findAll();
        verify(agentRepository, times(1)).findFirstByStatus("Available");
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
        verify(pendingOrderRepository, times(1)).delete(pendingOrder);
    }

    // Negative Test: No available agents for queued orders
    @Test
    public void testProcessQueuedOrders_NoAvailableAgents() {
        // Arrange
        PendingOrder pendingOrder = new PendingOrder();
        pendingOrder.setOrder(new Order());

        when(pendingOrderRepository.findAll()).thenReturn(List.of(pendingOrder));
        when(agentRepository.findFirstByStatus("Available")).thenReturn(Optional.empty());

        // Act
        deliveryService.processQueuedOrders();

        // Assert
        verify(pendingOrderRepository, times(1)).findAll();
        verify(agentRepository, times(1)).findFirstByStatus("Available");
        verify(deliveryRepository, never()).save(any(Delivery.class));
    }

    // Positive Test: Track delivery progress
    @Test
    public void testTrackDeliveryProgress_Success() {
        // Arrange
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(1L);
        delivery.setStatus("Assigning Agent");
        delivery.setEstimatedTimeOfArrival(LocalDateTime.now().plusMinutes(10));
        Agent agent = new Agent();
        agent.setAgentId(1L);
        delivery.setAgent(agent);

        when(deliveryRepository.findByStatusIn(anyList())).thenReturn(List.of(delivery));

        // Act
        deliveryService.trackDeliveryProgress();

        // Assert
        verify(deliveryRepository, times(1)).findByStatusIn(anyList());
        verify(deliveryRepository, times(1)).save(delivery);
        verify(notificationService, times(1)).notifyCustomer(any(Delivery.class), anyString());
    }

    // Negative Test: Exception during tracking
    @Test
    public void testTrackDeliveryProgress_Exception() {
        // Arrange
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(1L);
        delivery.setStatus("Assigning Agent");
        delivery.setEstimatedTimeOfArrival(LocalDateTime.now().plusMinutes(10));

        when(deliveryRepository.findByStatusIn(anyList())).thenReturn(List.of(delivery));
        doThrow(new RuntimeException("Database error")).when(deliveryRepository).save(any(Delivery.class));

        // Act
        deliveryService.trackDeliveryProgress();

        // Assert
        verify(deliveryRepository, times(1)).findByStatusIn(anyList());
        verify(deliveryRepository, times(1)).save(delivery);
    }

    // Positive Test: Scheduled tracking
    @Test
    public void testScheduledTrackDeliveryProgress() {
        // Act
        deliveryService.scheduledTrackDeliveryProgress();

        // Assert
        verify(deliveryRepository, times(1)).findByStatusIn(anyList());
    }
}