package com.saadoun.e_learning.model;

import com.saadoun.e_learning.service.email.EmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient;

    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    private Long userId;

    private Long courseId;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private Integer attempts;

    private LocalDateTime nextRetryAt;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

    @Column(length = 1000)
    private String lastError;
}
