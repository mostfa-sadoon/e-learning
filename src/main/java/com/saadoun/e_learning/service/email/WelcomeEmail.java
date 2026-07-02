package com.saadoun.e_learning.service.email;

import com.saadoun.e_learning.model.Student;

public class WelcomeEmail implements AbstractEmail{

    private final Student student;

    public WelcomeEmail(Student student) {
        this.student = student;
    }

    @Override
    public String getSubject() {
        return "Welcome";
    }

    @Override
    public String getBody() {
        return "Welcome " + student.getName() + " To Join elearning platform";
    }
}
