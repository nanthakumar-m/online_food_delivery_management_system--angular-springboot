package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByEmail(String email);
}
