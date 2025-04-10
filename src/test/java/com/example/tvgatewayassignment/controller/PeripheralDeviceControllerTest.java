package com.example.tvgatewayassignment.controller;

import com.example.tvgatewayassignment.entity.PeripheralDevice;
import com.example.tvgatewayassignment.service.PeripheralDeviceService;
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

@WebMvcTest(PeripheralDeviceController.class)
class PeripheralDeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeripheralDeviceService peripheralDeviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllDevices() throws Exception {
        PeripheralDevice device = new PeripheralDevice();
        device.setUid(1L);
        device.setVendor("VendorX");

        Mockito.when(peripheralDeviceService.getAllDevices(1L)).thenReturn(List.of(device));

        mockMvc.perform(get("/api/gateway/1/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vendor", is("VendorX")));
    }

    @Test
    void testGetDeviceById() throws Exception {
        PeripheralDevice device = new PeripheralDevice();
        device.setUid(2L);
        device.setVendor("VendorY");

        Mockito.when(peripheralDeviceService.getDevice(1L, 2L)).thenReturn(device);

        mockMvc.perform(get("/api/gateway/1/devices/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor", is("VendorY")));
    }

    @Test
    void testCreateDevice() throws Exception {
        PeripheralDevice device = new PeripheralDevice();
        device.setVendor("New Device");
        device.setStatus(PeripheralDevice.Status.OFFLINE);

        Mockito.when(peripheralDeviceService.createDevice(Mockito.eq(1L), any())).thenReturn(device);

        mockMvc.perform(post("/api/gateway/1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor", is("New Device")));
    }

    @Test
    void testUpdateDevice() throws Exception {
        PeripheralDevice updated = new PeripheralDevice();
        updated.setVendor("Updated Vendor");
        updated.setStatus(PeripheralDevice.Status.ONLINE);

        Mockito.when(peripheralDeviceService.updateDevice(Mockito.eq(1L), Mockito.eq(3L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/gateway/1/devices/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor", is("Updated Vendor")))
                .andExpect(jsonPath("$.status", is("ONLINE")));
    }

    @Test
    void testDeleteDevice() throws Exception {
        mockMvc.perform(delete("/api/gateway/1/devices/3"))
                .andExpect(status().isOk());

        Mockito.verify(peripheralDeviceService).deleteDevice(1L, 3L);
    }
}
