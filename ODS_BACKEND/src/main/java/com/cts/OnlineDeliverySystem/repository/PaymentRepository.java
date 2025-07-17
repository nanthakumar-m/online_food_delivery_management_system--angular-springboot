package com.cts.OnlineDeliverySystem.repository;


import com.cts.OnlineDeliverySystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    Payment findByStripePaymentIntendId(String id);
}
