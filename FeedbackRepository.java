package com.apu.assignment.repository;

import com.apu.assignment.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySaleId(Long saleId);
    List<Feedback> findByRating(Integer rating);
    List<Feedback> findByResponseIsNull();
    List<Feedback> findByResponseIsNotNull();
} 