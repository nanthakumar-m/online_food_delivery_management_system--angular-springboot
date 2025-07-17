package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.Delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderOrderId(Long orderId);
    List<Delivery> findByStatusIn(List<String> statuses);
}