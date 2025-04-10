package com.example.tvgatewayassignment.service;

import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.entity.PeripheralDevice;
import com.example.tvgatewayassignment.exception.BadRequestException;
import com.example.tvgatewayassignment.exception.ResourceNotFoundException;
import com.example.tvgatewayassignment.repository.PeripheralDeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PeripheralDeviceServiceTest {

    @InjectMocks
    private PeripheralDeviceService deviceService;

    @Mock
    private PeripheralDeviceRepository deviceRepository;

    @Mock
    private GatewayService gatewayService;

    private Gateway mockGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockGateway = new Gateway();
        mockGateway.setId(1L);
    }

    @Test
    void testGetAllDevices() {
        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.findByGatewayId(1L)).thenReturn(List.of(new PeripheralDevice(), new PeripheralDevice()));

        List<PeripheralDevice> devices = deviceService.getAllDevices(1L);
        assertEquals(2, devices.size());
    }

    @Test
    void testGetDeviceFound() {
        PeripheralDevice device = new PeripheralDevice();
        device.setUid(1L);
        device.setGateway(mockGateway);

        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        PeripheralDevice result = deviceService.getDevice(1L, 1L);
        assertEquals(1L, result.getUid());
    }

    @Test
    void testGetDeviceNotFound() {
        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deviceService.getDevice(1L, 99L));
    }

    @Test
    void testCreateDeviceSuccess() {
        PeripheralDevice newDevice = new PeripheralDevice();
        mockGateway.setPeripheralDevices(List.of(new PeripheralDevice())); // 1 device

        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.save(newDevice)).thenReturn(newDevice);

        PeripheralDevice result = deviceService.createDevice(1L, newDevice);
        assertEquals(newDevice, result);
    }

    @Test
    void testCreateDeviceTooManyDevices() {
        List<PeripheralDevice> tenDevices = java.util.stream.IntStream.range(0, 10)
                .mapToObj(i -> new PeripheralDevice()).toList();
        mockGateway.setPeripheralDevices(tenDevices);

        PeripheralDevice newDevice = new PeripheralDevice();

        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);

        assertThrows(BadRequestException.class, () -> deviceService.createDevice(1L, newDevice));
    }

    @Test
    void testUpdateDevice() {
        PeripheralDevice existing = new PeripheralDevice();
        existing.setUid(1L);
        existing.setGateway(mockGateway);

        PeripheralDevice updated = new PeripheralDevice();
        updated.setVendor("New Vendor");
        updated.setStatus(PeripheralDevice.Status.ONLINE);

        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(deviceRepository.save(any())).thenReturn(existing);

        PeripheralDevice result = deviceService.updateDevice(1L, 1L, updated);
        assertEquals("New Vendor", result.getVendor());
        assertEquals("ONLINE", result.getStatus().name());
    }

    @Test
    void testDeleteDevice() {
        PeripheralDevice device = new PeripheralDevice();
        device.setUid(1L);
        device.setGateway(mockGateway);

        when(gatewayService.getGateway(1L)).thenReturn(mockGateway);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        doNothing().when(deviceRepository).delete(device);

        deviceService.deleteDevice(1L, 1L);
        verify(deviceRepository).delete(device);
    }
}
