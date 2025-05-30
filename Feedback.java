package com.apu.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(nullable = false)
    private Integer rating;  // 1-5 stars

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime feedbackDate;

    private String response;  // Response from management
    private LocalDateTime responseDate;
} 