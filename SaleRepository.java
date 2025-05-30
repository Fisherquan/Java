package com.apu.assignment.repository;

import com.apu.assignment.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomerId(Long customerId);
    List<Sale> findBySalesmanId(Long salesmanId);
    List<Sale> findByPaymentStatus(String paymentStatus);
    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
} 