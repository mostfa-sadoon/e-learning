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
@Table(name = "email_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType emailType;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @PrePersist
    public void prePersist() {
        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }
}
