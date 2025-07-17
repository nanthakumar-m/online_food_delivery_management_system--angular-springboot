package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Agent;
import com.cts.OnlineDeliverySystem.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    // Create a new agent
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    // Delete an agent by ID
    public void deleteAgent(Long id) {
        agentRepository.deleteById(id);
    }

    // Get all agents
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
}