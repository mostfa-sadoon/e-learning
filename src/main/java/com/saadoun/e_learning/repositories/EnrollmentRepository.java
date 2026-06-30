package com.saadoun.e_learning.repositories;

import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository  extends JpaRepository<Enrollment, EnrollmentId> {
}
