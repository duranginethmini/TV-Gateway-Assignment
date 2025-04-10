package com.example.tvgatewayassignment.controller;

import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.service.GatewayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GatewayController.class)
class GatewayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GatewayService gatewayService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllGateways() throws Exception {
        Gateway g = new Gateway();
        g.setId(1L);
        g.setName("Test Gateway");

        Mockito.when(gatewayService.getAllGateway()).thenReturn(List.of(g));

        mockMvc.perform(get("/api/gateway"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Test Gateway")));
    }

    @Test
    void testGetGatewayById() throws Exception {
        Gateway g = new Gateway();
        g.setId(1L);
        g.setName("Sample Gateway");

        Mockito.when(gatewayService.getGateway(1L)).thenReturn(g);

        mockMvc.perform(get("/api/gateway/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Sample Gateway")));
    }

    @Test
    void testCreateGateway() throws Exception {
        Gateway g = new Gateway();
        g.setName("New Gateway");
        g.setSerialNumber("ABC123");
        g.setIpv4Address("192.168.1.1");

        Mockito.when(gatewayService.createGateway(any())).thenReturn(g);

        mockMvc.perform(post("/api/gateway")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(g)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Gateway")));
    }

    @Test
    void testUpdateGateway() throws Exception {
        Gateway g = new Gateway();
        g.setName("Updated Gateway");
        g.setSerialNumber("XYZ789");
        g.setIpv4Address("10.0.0.2");

        Mockito.when(gatewayService.updateGateway(Mockito.eq(1L), any())).thenReturn(g);

        mockMvc.perform(put("/api/gateway/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(g)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Gateway")));
    }

    @Test
    void testDeleteGateway() throws Exception {
        mockMvc.perform(delete("/api/gateway/1"))
                .andExpect(status().isOk());

        Mockito.verify(gatewayService).deleteGateway(1L);
    }
}

