package com.apu.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "salesman_id", nullable = false)
    private User salesman;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @Column(nullable = false)
    private LocalDateTime saleDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String paymentMethod;
    private String remarks;
}

enum PaymentStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    REFUNDED
} 