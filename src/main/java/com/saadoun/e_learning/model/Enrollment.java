package com.saadoun.e_learning.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import com.saadoun.e_learning.model.EnrollmentId;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @EmbeddedId
    private  EnrollmentId id = new EnrollmentId();


    @ManyToOne
    @MapsId("studentId")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JsonIgnore
    private Course course;

    private LocalDate enrolledAt;
    private String grade;


    @PrePersist
    public void prePersist() {
        if (enrolledAt == null) {
            enrolledAt = LocalDate.now();
        }
    }

}

