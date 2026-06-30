package com.saadoun.e_learning.repositories;


import com.saadoun.e_learning.model.Student;
import com.saadoun.e_learning.dto.projection.StudentCourseRowProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository <Student,Long >, JpaSpecificationExecutor<Student> {


    // Fetch student with courses in one query (avoids N+1)
    @Query("""
        SELECT DISTINCT s
        FROM Student s
        LEFT JOIN FETCH s.enrollments e
        LEFT JOIN FETCH e.course
        WHERE s.id = :id
    """)
    Optional<Student> findByIdWithCourses(@Param("id") Long id);

    // get all students with courses no pagination
    @Query("""
        SELECT s.id AS studentId,
               s.name AS studentName,
               s.email AS studentEmail,
               c.id AS courseId,
               c.title AS courseName
        FROM Student s
        LEFT JOIN s.enrollments e
        LEFT JOIN e.course c
    """)
    List<StudentCourseRowProjection> findAllWithCourses();

    @Query("""
            SELECT s from Student s
            """
    )
    Page<Student> findAllStudents(Pageable pageable);


    @Query("""
            SELECT s
            FROM Student s
            LEFT JOIN FETCH s.enrollments e
            LEFT JOIN FETCH e.course c
            where s.id IN :ids
            """)
    List<Student> getStudentsWithCourses(List<Long> ids);


    @EntityGraph(attributePaths = {
            "enrollments",
            "enrollments.course"
    })
    Optional<Student> findById(Long id);

}
