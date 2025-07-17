package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {
}