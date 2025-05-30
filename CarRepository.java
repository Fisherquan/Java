package com.apu.assignment.repository;

import com.apu.assignment.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(String status);
    List<Car> findByBrandAndModel(String brand, String model);
    List<Car> findByYearBetween(Integer startYear, Integer endYear);
    List<Car> findByPriceLessThanEqual(Double maxPrice);
} 