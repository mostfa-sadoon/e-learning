package com.saadoun.e_learning.model;

import com.saadoun.e_learning.service.email.EmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_retry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType emailType;

    private Long studentId;

    private Long courseId;

    @Column(nullable = false)
    @Builder.Default
    private Integer attempts = 0;

    @Column(length = 2000)
    private String lastError;

    private LocalDateTime nextRetryAt;
}
