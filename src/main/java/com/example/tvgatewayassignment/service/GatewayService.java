package com.example.tvgatewayassignment.service;


import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.exception.ResourceNotFoundException;
import com.example.tvgatewayassignment.repository.GatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    public List<Gateway>getAllGateway() {
        return gatewayRepository.findAll();
    }

    public Gateway getGateway(Long id) {
        return gatewayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gateway not found with id" + id));
    }

    public Gateway createGateway (Gateway gateway){
        return gatewayRepository.save(gateway);
    }

    public Gateway updateGateway(Long id , Gateway updated) {
        Gateway gateway = getGateway(id);
        gateway.setSerialNumber(updated.getSerialNumber());
        gateway.setName(updated.getName());
        gateway.setIpv4Address(updated.getIpv4Address());
        return gatewayRepository.save(gateway);
    }

    public Gateway partialUpdateGateway(Long id, Gateway updated) {
        Gateway gateway = getGateway(id);


        if (updated.getSerialNumber() != null) {
            gateway.setSerialNumber(updated.getSerialNumber());
        }
        if (updated.getName() != null) {
            gateway.setName(updated.getName());
        }
        if (updated.getIpv4Address() != null) {
            gateway.setIpv4Address(updated.getIpv4Address());
        }

        return gatewayRepository.save(gateway);
    }

    public void deleteGateway(Long id) {
        Gateway gateway = getGateway(id);
        gatewayRepository.delete(gateway);
    }
}
