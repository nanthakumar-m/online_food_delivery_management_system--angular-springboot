// package com.cts.OnlineDeliverySystem.controller;

// import com.cts.OnlineDeliverySystem.NoSecurityConfig;
// import com.cts.OnlineDeliverySystem.entity.Agent;
// import com.cts.OnlineDeliverySystem.service.AgentService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.mockito.ArgumentMatchers.any;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(controllers = AgentController.class, excludeAutoConfiguration = {NoSecurityConfig.class})
// public class AgentControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Mock
//     private AgentService agentService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     public void testCreateAgent() throws Exception {
//         // Arrange
//         Agent agent = new Agent();
//         agent.setAgentId(1L);
//         agent.setName("John Doe");
//         agent.setContactNumber("1234567890");
//         agent.setStatus("Available");

//         Mockito.when(agentService.createAgent(any(Agent.class))).thenReturn(agent);

//         // Act & Assert
//         mockMvc.perform(post("/admin/agents/create")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(agent)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().json(objectMapper.writeValueAsString(agent)));
//     }
// }