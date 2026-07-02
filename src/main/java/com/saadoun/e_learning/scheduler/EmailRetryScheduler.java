package com.saadoun.e_learning.scheduler;


import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.EmailQueue;
import com.saadoun.e_learning.model.Student;
import com.saadoun.e_learning.repositories.CourseRepository;
import com.saadoun.e_learning.repositories.StudentRepository;
import com.saadoun.e_learning.service.email.AbstractEmail;
import com.saadoun.e_learning.service.email.EmailFactory;
import com.saadoun.e_learning.service.email.EmailQueueService;
import com.saadoun.e_learning.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EmailRetryScheduler {


    private final EmailQueueService emailQueueService;
    private final EmailFactory emailFactory;
    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Scheduled(fixedDelay = 60000)
    public void retryFailedEmails() {


        List<EmailQueue> emails = emailQueueService.getEmailsToRetry();

        for(EmailQueue queue : emails){
            try {
                Student student = studentRepository.findById(queue.getUserId()).orElseThrow();
                Course course = courseRepository.findById(queue.getCourseId()).orElseThrow();


                AbstractEmail email = emailFactory.createEmail(
                        queue.getEmailType(),
                        student,
                        course
                );

                emailService.send(student.getEmail(),email);
                emailQueueService.markAsSent(queue);

            } catch (Exception e) {
                emailQueueService.markAsFailed(queue, e);

                throw new RuntimeException(e);
            }
        }

    }


}
