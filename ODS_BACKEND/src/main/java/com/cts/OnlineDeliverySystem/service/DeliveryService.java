package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Agent;
import com.cts.OnlineDeliverySystem.entity.Delivery;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.PendingOrder;
import com.cts.OnlineDeliverySystem.repository.AgentRepository;
import com.cts.OnlineDeliverySystem.repository.DeliveryRepository;
import com.cts.OnlineDeliverySystem.repository.PendingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PendingOrderRepository pendingOrderRepository;

    @Autowired
    private NotificationService notificationService;

    public String assignAgentToOrder(Order order) {
        // Find an available agent
        Optional<Agent> availableAgent = agentRepository.findFirstByStatus("Available");

        if (availableAgent.isPresent()) {
            Agent agent = availableAgent.get();

            // Create a new delivery
            Delivery delivery = new Delivery();
            delivery.setOrder(order);
            delivery.setAgent(agent);
            delivery.setStatus("Assigning Agent");
            delivery.setEstimatedTimeOfArrival(LocalDateTime.now().plusMinutes(15));
            deliveryRepository.save(delivery);

            // Update agent status to "Inservice"
            agent.setStatus("Inservice");
            agentRepository.save(agent);

            // Call trackDeliveryProgress to update status
            trackDeliveryProgress();

            return "Order assigned to agent: " + agent.getName();
        } else {
            // Queue the order
            PendingOrder pendingOrder = new PendingOrder();
            pendingOrder.setOrder(order);
            pendingOrderRepository.save(pendingOrder);

            return "No available agents. Order has been queued for assignment.";
        }
    }

    //  check for available agents and assign queued orders
    public void processQueuedOrders() {
        List<PendingOrder> pendingOrders = pendingOrderRepository.findAll();

        for (PendingOrder pendingOrder : pendingOrders) {
            try {
                Order order = pendingOrder.getOrder();

                // Find an available agent
                Optional<Agent> availableAgent = agentRepository.findFirstByStatus("Available");

                if (availableAgent.isPresent()) {
                    Agent agent = availableAgent.get();

                    // Create a new delivery
                    Delivery delivery = new Delivery();
                    delivery.setOrder(order);
                    delivery.setAgent(agent);
                    delivery.setStatus("Assigning Agent");
                    delivery.setEstimatedTimeOfArrival(LocalDateTime.now().plusMinutes(15));
                    deliveryRepository.save(delivery);


                    agent.setStatus("Inservice");
                    agentRepository.save(agent);

                    // Remove the order from the pending queue
                    pendingOrderRepository.delete(pendingOrder);

                    System.out.println("Order ID " + order.getOrderId() + " assigned to Agent ID " + agent.getAgentId());
                } else {
                    System.out.println("No available agents. Pending orders will be retried later.");
                    break;
                }
            } catch (Exception e) {
                System.err.println("Error processing pending order ID " + pendingOrder.getId() + ": " + e.getMessage());
            }
        }
        // Call trackDeliveryProgress
        trackDeliveryProgress();
    }

    // Track delivery progress
    public void trackDeliveryProgress() {
        // Fetch only deliveries that are not yet delivered
        List<Delivery> deliveries = deliveryRepository.findByStatusIn(List.of("Assigning Agent", "Out for Delivery", "Delivers in 5 mins"));

        for (Delivery delivery : deliveries) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime eta = delivery.getEstimatedTimeOfArrival();

            try {
                if (delivery.getStatus().equals("Assigning Agent") && now.isAfter(eta.minusMinutes(14))) {
                    delivery.setStatus("Out for Delivery");
                    deliveryRepository.save(delivery);
                    notificationService.notifyCustomer(delivery, "Your order is out for delivery.");
                } else if (delivery.getStatus().equals("Out for Delivery") && now.isAfter(eta.minusMinutes(5))) {
                    delivery.setStatus("Delivers in 5 mins");
                    deliveryRepository.save(delivery);
                    notificationService.notifyCustomer(delivery, "Your order will be delivered in 5 minutes.");
                } else if (delivery.getStatus().equals("Delivers in 5 mins") && now.isAfter(eta)) {
                    delivery.setStatus("Delivered");
                    deliveryRepository.save(delivery);
                    notificationService.notifyCustomer(delivery, "Your order has been delivered.");

                    // Set the agent's status back to "Available"
                    Agent agent = delivery.getAgent();
                    agent.setStatus("Available");
                    agentRepository.save(agent);
                    System.out.println("Agent ID " + agent.getAgentId() + " is now available.");
                }
            } catch (Exception e) {
                System.err.println("Error tracking delivery ID " + delivery.getDeliveryId() + ": " + e.getMessage());
            }
        }
    }

    // Scheduler to call trackDeliveryProgress every minute
    @Scheduled(fixedRate = 60000) // Runs every 1 minute
    public void scheduledTrackDeliveryProgress() {
        trackDeliveryProgress();
    }
}