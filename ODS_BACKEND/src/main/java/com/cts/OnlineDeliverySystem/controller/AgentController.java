package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.entity.Agent;
import com.cts.OnlineDeliverySystem.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/admin/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    // Create a new agent (Admin only)
    @PostMapping("/create")
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        Agent createdAgent = agentService.createAgent(agent);
        return ResponseEntity.ok(createdAgent);
    }

    // Delete an agent by ID (Admin only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAgent(@PathVariable Long id)
    {
        agentService.deleteAgent(id);
        return ResponseEntity.ok("Agent deleted successfully.");
    }

    // Get all agents (Admin only)
    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgents( String role) {
        List<Agent> agents = agentService.getAllAgents();
        return ResponseEntity.ok(agents);
    }
}