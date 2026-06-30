package com.saadoun.e_learning.repositories;

import com.saadoun.e_learning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository <Course,Long> {
}
