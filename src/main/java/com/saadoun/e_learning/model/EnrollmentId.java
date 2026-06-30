package com.saadoun.e_learning.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public  class EnrollmentId implements Serializable {
    private Long studentId;
    private Long courseId;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
