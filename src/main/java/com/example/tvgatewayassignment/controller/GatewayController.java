package com.example.tvgatewayassignment.controller;

import com.example.tvgatewayassignment.entity.Gateway;
import com.example.tvgatewayassignment.service.GatewayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping
    public List<Gateway> getAll() {
        return gatewayService.getAllGateway();
    }

    @GetMapping("/{id}")
    public Gateway getOne(@PathVariable Long id) {
        return gatewayService.getGateway(id);
    }

    @PostMapping
    public Gateway create (@Valid @RequestBody Gateway gateway) {
        return gatewayService.createGateway(gateway);
    }

    @PutMapping("/{id}")
    public Gateway update (@PathVariable Long id, @Valid @RequestBody Gateway gateway) {
         return gatewayService.updateGateway(id, gateway);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        gatewayService.deleteGateway(id);
    }

}
