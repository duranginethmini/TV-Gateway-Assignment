package com.example.tvgatewayassignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeripheralDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    private String vendor;

    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "gateway_id", nullable = false)
    @JsonIgnore
    private Gateway gateway;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    public enum Status {
        ONLINE, OFFLINE
    }
}
