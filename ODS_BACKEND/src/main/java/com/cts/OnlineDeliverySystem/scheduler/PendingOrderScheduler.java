package com.cts.OnlineDeliverySystem.scheduler;

import com.cts.OnlineDeliverySystem.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PendingOrderScheduler {

    @Autowired
    private DeliveryService deliveryService;

    // Run every 2 minute
    @Scheduled(fixedRate = 120000)
    public void processPendingOrders() {
        deliveryService.processQueuedOrders();
    }
}
