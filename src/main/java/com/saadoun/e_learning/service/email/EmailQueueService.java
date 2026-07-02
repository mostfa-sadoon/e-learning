package com.saadoun.e_learning.service.email;


import com.saadoun.e_learning.model.EmailQueue;
import com.saadoun.e_learning.model.EmailStatus;
import com.saadoun.e_learning.repositories.EmailQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailQueueService {

    private final EmailQueueRepository repository;

    public EmailQueue createPending(
            String recipient,
            EmailType emailType,
            Long studentId,
            Long courseId
    ) {

        EmailQueue queue = EmailQueue.builder()
                .recipient(recipient)
                .emailType(emailType)
                .userId(studentId)
                .courseId(courseId)
                .status(EmailStatus.PENDING)
                .attempts(0)
                .build();

        return repository.save(queue);
    }

    public void markAsSent(EmailQueue queue) {

        queue.setStatus(EmailStatus.SENT);
        queue.setSentAt(LocalDateTime.now());

        repository.save(queue);
    }

    public void markAsFailed(EmailQueue queue, Exception ex) {

        int attempts = queue.getAttempts() + 1;

        queue.setAttempts(attempts);
        queue.setStatus(EmailStatus.FAILED);
        queue.setLastError(ex.getMessage());

        queue.setNextRetryAt(nextRetry(attempts));

        repository.save(queue);
    }

    public List<EmailQueue> getEmailsToRetry() {

        return repository.findTop50ByStatusAndNextRetryAtBefore(
                EmailStatus.FAILED,
                LocalDateTime.now()
        );
    }

    private LocalDateTime nextRetry(int attempts) {

        return switch (attempts) {

            case 1 -> LocalDateTime.now().plusMinutes(1);

            case 2 -> LocalDateTime.now().plusMinutes(5);

            case 3 -> LocalDateTime.now().plusMinutes(15);

            case 4 -> LocalDateTime.now().plusMinutes(30);

            default -> LocalDateTime.now().plusHours(1);
        };
    }


}
