package com.saadoun.e_learning.service.email;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    
    public void send(String to, AbstractEmail email) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8"
            );
            helper.setTo(to);
            helper.setSubject(email.getSubject());
            // ✅ IMPORTANT: true = HTML enabled
            helper.setText(email.getBody(), true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
