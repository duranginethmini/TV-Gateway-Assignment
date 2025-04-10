package com.example.tvgatewayassignment.controller;


import com.example.tvgatewayassignment.entity.PeripheralDevice;
import com.example.tvgatewayassignment.service.PeripheralDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/gateway/{gatewayId}/devices")
public class PeripheralDeviceController {

    @Autowired
    private PeripheralDeviceService peripheralDeviceService;

    @GetMapping
    public List<PeripheralDevice> getAll (@PathVariable Long gatewayId){
                   return peripheralDeviceService.getAllDevices(gatewayId);
    }

    @GetMapping("/{uid}")
    public PeripheralDevice getOne (@PathVariable Long gatewayId , @PathVariable Long uid){
        return peripheralDeviceService.getDevice(gatewayId,uid);
    }

    @PostMapping
    public PeripheralDevice create (@PathVariable Long gatewayId , @Valid @RequestBody PeripheralDevice peripheralDevice) {
        return peripheralDeviceService.createDevice(gatewayId,peripheralDevice);
    }

    @PutMapping("/{uid}")
    public PeripheralDevice update(@PathVariable Long gatewayId, @PathVariable Long uid,@Valid @RequestBody PeripheralDevice peripheralDevice ) {
          return peripheralDeviceService.updateDevice(gatewayId,uid,peripheralDevice);
    }

    @PatchMapping("/{uid}")
    public PeripheralDevice partialUpdate(@PathVariable Long gatewayId, @PathVariable Long uid, @RequestBody PeripheralDevice peripheralDevice) {
        return peripheralDeviceService.partialUpdateDevice(gatewayId, uid, peripheralDevice);
    }

    @DeleteMapping("/{uid}")
    public void delete(@PathVariable Long gatewayId, @PathVariable Long uid) {
           peripheralDeviceService.deleteDevice(gatewayId,uid);
    }
}
