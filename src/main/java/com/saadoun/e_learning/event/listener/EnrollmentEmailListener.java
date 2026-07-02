package com.saadoun.e_learning.event.listener;


import com.saadoun.e_learning.event.EnrollmentCreatedEvent;
import com.saadoun.e_learning.service.email.AbstractEmail;
import com.saadoun.e_learning.service.email.EmailFactory;
import com.saadoun.e_learning.service.email.EmailService;
import com.saadoun.e_learning.service.email.EmailType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnrollmentEmailListener {

     private final EmailFactory emailFactory;
     private final EmailService emailService;

     @Async
     @EventListener
     public void handel(EnrollmentCreatedEvent event){

         try{
             AbstractEmail email = emailFactory.createEmail(EmailType.ENROLLMENT,event.student(),event.course());
             emailService.send(event.student().getEmail(),email);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

     }

}
