package com.saadoun.e_learning.event.listener;


import com.saadoun.e_learning.event.EnrollmentCreatedEvent;
import com.saadoun.e_learning.model.EmailQueue;
import com.saadoun.e_learning.service.email.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnrollmentEmailListener {

     private final EmailFactory emailFactory;
     private final EmailService emailService;
     private final EmailQueueService emailQueueService;

     @Async
     @EventListener
     public void handel(EnrollmentCreatedEvent event){
         EmailQueue queue = emailQueueService.createPending(
                 event.student().getEmail(),
                 EmailType.ENROLLMENT,
                 event.student().getId(),
                 event.course().getId()
         );
         try{
             AbstractEmail email = emailFactory.createEmail(EmailType.ENROLLMENT,event.student(),event.course());
             // send mail to user
             emailService.send(event.student().getEmail(),email);
             // save mail in database as send
             emailQueueService.markAsSent(queue);
         } catch (Exception e) {
             // save field emails to send mails to them retrey
             emailQueueService.markAsFailed(queue,e);
             throw new RuntimeException(e);
         }

     }

}
