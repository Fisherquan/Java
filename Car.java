package com.apu.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    private CarStatus status;

    @Column(nullable = false)
    private String registrationNumber;

    private String imageUrl;
}

enum CarStatus {
    AVAILABLE,
    SOLD,
    UNDER_MAINTENANCE,
    RESERVED
} 