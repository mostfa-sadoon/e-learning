package com.saadoun.e_learning.dto.projection;

public interface StudentCourseRowProjection {
    Long getStudentId();
    String getStudentName();
    String getStudentEmail();
    Long getCourseId();
    String getCourseName();
}
