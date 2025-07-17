package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Agent;
import com.cts.OnlineDeliverySystem.repository.AgentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private AgentService agentService;

    // Test for createAgent method
    @Test
    public void testCreateAgent() {
        // Arrange
        Agent agent = new Agent();
        agent.setAgentId(1L);
        agent.setName("John Doe");
        agent.setContactNumber("1234567890");
        agent.setStatus("Available");

        when(agentRepository.save(any(Agent.class))).thenReturn(agent);

        // Act
        Agent createdAgent = agentService.createAgent(agent);

        // Assert
        assertEquals(agent.getAgentId(), createdAgent.getAgentId());
        assertEquals(agent.getName(), createdAgent.getName());
        assertEquals(agent.getContactNumber(), createdAgent.getContactNumber());
        assertEquals(agent.getStatus(), createdAgent.getStatus());

        verify(agentRepository, times(1)).save(agent);
    }

    // Test for deleteAgent method
    @Test
    public void testDeleteAgent() {
        // Arrange
        Long agentId = 1L;
        doNothing().when(agentRepository).deleteById(agentId);

        // Act
        agentService.deleteAgent(agentId);

        // Assert
        verify(agentRepository, times(1)).deleteById(agentId);
    }

    // Test for getAllAgents method
    @Test
    public void testGetAllAgents() {
        // Arrange
        Agent agent1 = new Agent();
        agent1.setAgentId(1L);
        agent1.setName("John Doe");
        agent1.setContactNumber("1234567890");
        agent1.setStatus("Available");

        Agent agent2 = new Agent();
        agent2.setAgentId(2L);
        agent2.setName("Jane Smith");
        agent2.setContactNumber("0987654321");
        agent2.setStatus("Available");

        List<Agent> agents = Arrays.asList(agent1, agent2);
        when(agentRepository.findAll()).thenReturn(agents);

        // Act
        List<Agent> retrievedAgents = agentService.getAllAgents();

        // Assert
        assertEquals(2, retrievedAgents.size());
        assertEquals(agent1.getAgentId(), retrievedAgents.get(0).getAgentId());
        assertEquals(agent2.getAgentId(), retrievedAgents.get(1).getAgentId());

        verify(agentRepository, times(1)).findAll();
    }
}