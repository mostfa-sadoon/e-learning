package com.saadoun.e_learning.event;

import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Student;

public record EnrollmentCreatedEvent(
        Student student,
        Course course
) {
}
