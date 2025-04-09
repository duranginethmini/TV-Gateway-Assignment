package com.example.tvgatewayassignment.repository;

import com.example.tvgatewayassignment.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
}