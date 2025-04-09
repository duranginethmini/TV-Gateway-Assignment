package com.example.tvgatewayassignment.repository;

import com.example.tvgatewayassignment.entity.PeripheralDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeripheralDeviceRepository extends JpaRepository<PeripheralDevice, Long> {
    List<PeripheralDevice> findByGatewayId(Long gatewayId);
}

