package com.example.tvgatewayassignment.service;

import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.exception.ResourceNotFoundException;
import com.example.tvgatewayassignment.repository.GatewayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GatewayServiceTest {

    @InjectMocks
    private GatewayService gatewayService;

    @Mock
    private GatewayRepository gatewayRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGateways() {
        when(gatewayRepository.findAll()).thenReturn(List.of(new Gateway(), new Gateway()));
        List<Gateway> result = gatewayService.getAllGateway();
        assertEquals(2, result.size());
    }

    @Test
    void testGetGatewayByIdFound() {
        Gateway gateway = new Gateway();
        gateway.setId(1L);
        when(gatewayRepository.findById(1L)).thenReturn(Optional.of(gateway));

        Gateway result = gatewayService.getGateway(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetGatewayByIdNotFound() {
        when(gatewayRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> gatewayService.getGateway(1L));
    }

    @Test
    void testCreateGateway() {
        Gateway gateway = new Gateway();
        gateway.setName("Test");
        when(gatewayRepository.save(gateway)).thenReturn(gateway);

        Gateway result = gatewayService.createGateway(gateway);
        assertEquals("Test", result.getName());
    }

    @Test
    void testUpdateGateway() {
        Gateway existing = new Gateway();
        existing.setId(1L);
        existing.setName("Old");
        existing.setSerialNumber("123");
        existing.setIpv4Address("192.168.0.1");

        Gateway updated = new Gateway();
        updated.setName("New");
        updated.setSerialNumber("456");
        updated.setIpv4Address("10.0.0.1");

        when(gatewayRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gatewayRepository.save(any())).thenReturn(existing);

        Gateway result = gatewayService.updateGateway(1L, updated);
        assertEquals("New", result.getName());
        assertEquals("456", result.getSerialNumber());
        assertEquals("10.0.0.1", result.getIpv4Address());
    }

    @Test
    void testDeleteGateway() {
        Gateway gateway = new Gateway();
        when(gatewayRepository.findById(1L)).thenReturn(Optional.of(gateway));
        doNothing().when(gatewayRepository).delete(gateway);

        gatewayService.deleteGateway(1L);
        verify(gatewayRepository, times(1)).delete(gateway);
    }
}
