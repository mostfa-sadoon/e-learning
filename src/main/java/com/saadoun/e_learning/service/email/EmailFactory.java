package com.saadoun.e_learning.service.email;

import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Student;
import org.springframework.stereotype.Component;

@Component
public class EmailFactory {

    public AbstractEmail createEmail(
            EmailType type,
            Student student,
            Course course
    ) {
        return switch (type) {

            case ENROLLMENT -> new EnrollmentEmail(student, course);

            case WELCOME -> new WelcomeEmail(student);

            default -> throw new IllegalArgumentException("Unsupported email type");
        };
    }
}
