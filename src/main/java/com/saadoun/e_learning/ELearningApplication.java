package com.saadoun.e_learning;

import com.github.javafaker.Faker;
import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.model.Student;
import com.saadoun.e_learning.repositories.CourseRepository;
import com.saadoun.e_learning.repositories.EnrollmentRepository;
import com.saadoun.e_learning.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableAsync
public class ELearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearningApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository
    ) {
        return args -> {

            if (studentRepository.count() > 0) {
                System.out.println("Data already exists!");
                return;
            }

            Faker faker = new Faker();
            Random random = new Random();

            // Create 7 courses
            List<Course> courses = List.of(
                    Course.builder().title("Spring Boot").build(),
                    Course.builder().title("Java").build(),
                    Course.builder().title("Hibernate").build(),
                    Course.builder().title("Docker").build(),
                    Course.builder().title("Kafka").build(),
                    Course.builder().title("Microservices").build(),
                    Course.builder().title("AWS").build()
            );

            courseRepository.saveAll(courses);

            // Create 50 students
            List<Student> students = new ArrayList<>();

            for (int i = 0; i < 50; i++) {
                Student student = new Student();
                student.setName(faker.name().fullName());
                students.add(student);
            }

            studentRepository.saveAll(students);

            List<Enrollment> enrollments = new ArrayList<>();

            for (Student student : students) {

                // Copy courses and shuffle them
                List<Course> shuffledCourses = new ArrayList<>(courses);
                Collections.shuffle(shuffledCourses);

                // Student joins between 5 and 7 courses
                int numberOfCourses = random.nextInt(3) + 5; // 5,6,7

                for (int i = 0; i < numberOfCourses; i++) {
                    Enrollment enrollment = new Enrollment();

                    enrollment.setStudent(student);
                    enrollment.setCourse(shuffledCourses.get(i));
                    enrollment.setEnrolledAt(LocalDate.now().minusDays(random.nextInt(365)));

                    String[] grades = {"A+", "A", "B+", "B", "C+"};
                    enrollment.setGrade(grades[random.nextInt(grades.length)]);

                    enrollments.add(enrollment);
                }
            }

            enrollmentRepository.saveAll(enrollments);

            System.out.println("50 students and enrollments created successfully!");
        };
    }
}
