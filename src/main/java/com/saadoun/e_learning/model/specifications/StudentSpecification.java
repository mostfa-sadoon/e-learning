package com.saadoun.e_learning.model.specifications;

import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.model.Student;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> hasName(String name) {
        return (root, query, cb) ->
                name == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Student> hasCourse(String courseTitle) {
        return (root, query, cb) -> {

            Join<Student, Enrollment> enrollmentJoin =
                    root.join("enrollments");

            Join<Enrollment, Course> courseJoin =
                    enrollmentJoin.join("course");

            return cb.equal(
                    courseJoin.get("title"),
                    courseTitle
            );
        };
    }
}
