package com.saadoun.e_learning.service.email;

import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Student;

public class EnrollmentEmail implements AbstractEmail{

    private final Student student;
    private final Course course;

    public EnrollmentEmail(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public String getSubject() {
        return "Course Enrollment";
    }

    @Override
    public String getBody() {
        return """
                Hello %s,

                Congratulations!

                You have successfully enrolled in the course:

                %s

                Best wishes.
                """
                .formatted(student.getName(), course.getTitle());
    }
}
