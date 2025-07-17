package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.Agent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findFirstByStatus(String status);
}
