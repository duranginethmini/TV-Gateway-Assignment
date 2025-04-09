package com.example.tvgatewayassignment.service;

import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.entity.PeripheralDevice;
import com.example.tvgatewayassignment.exception.BadRequestException;
import com.example.tvgatewayassignment.exception.ResourceNotFoundException;
import com.example.tvgatewayassignment.repository.PeripheralDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeripheralDeviceService {

    @Autowired
    private PeripheralDeviceRepository peripheralDeviceRepository;

    @Autowired
    private GatewayService gatewayService;

    public List<PeripheralDevice> getAllDevices(Long gatewayId){
        gatewayService.getGateway(gatewayId);
        return peripheralDeviceRepository.findByGatewayId(gatewayId);
    }

    public PeripheralDevice getDevice (Long gatewayId , Long uid) {
        gatewayService.getGateway(gatewayId);
        return peripheralDeviceRepository.findById(uid)
                .filter(d -> d.getGateway().getId().equals(gatewayId))
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with ID" + uid));
    }

    public PeripheralDevice createDevice (Long gatewayId , PeripheralDevice peripheralDevice) {
        Gateway gateway = gatewayService.getGateway(gatewayId);
        if(gateway.getPeripheralDevices().size() >= 10) {
            throw new BadRequestException("Gateway cannot have more than 10 devices");
        }
        peripheralDevice.setGateway(gateway);
        return peripheralDeviceRepository.save(peripheralDevice);
    }

    public PeripheralDevice updateDevice (Long gatewayId , Long uid ,  PeripheralDevice updated) {
        PeripheralDevice peripheralDevice =  getDevice(gatewayId,uid);
        peripheralDevice.setVendor(updated.getVendor());
        peripheralDevice.setStatus(updated.getStatus());
        return peripheralDeviceRepository.save(peripheralDevice);
    }

    public void deleteDevice (Long gatewayId ,  Long uid) {
         PeripheralDevice peripheralDevice = getDevice(gatewayId,uid);
         peripheralDeviceRepository.delete(peripheralDevice);
    }
}
