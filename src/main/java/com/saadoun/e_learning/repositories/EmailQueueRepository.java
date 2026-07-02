package com.saadoun.e_learning.repositories;

import com.saadoun.e_learning.model.EmailQueue;
import com.saadoun.e_learning.model.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailQueueRepository extends JpaRepository<EmailQueue, Long> {

    List<EmailQueue> findTop50ByStatusAndNextRetryAtBefore(
            EmailStatus status,
            LocalDateTime now
    );
}
