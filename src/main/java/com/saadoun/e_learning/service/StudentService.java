package com.saadoun.e_learning.service;


import com.saadoun.e_learning.dto.StudentDto;
import com.saadoun.e_learning.dto.req.EnrollReqDto;
import com.saadoun.e_learning.dto.req.StudentReqDto;
import com.saadoun.e_learning.event.EnrollmentCreatedEvent;
import com.saadoun.e_learning.event.publisher.EnrollmentEventPublisher;
import com.saadoun.e_learning.mapper.StudentMapper;
import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.model.Student;
import com.saadoun.e_learning.model.specifications.StudentSpecification;
import com.saadoun.e_learning.repositories.CourseRepository;
import com.saadoun.e_learning.repositories.EnrollmentRepository;
import com.saadoun.e_learning.repositories.StudentRepository;
import com.saadoun.e_learning.dto.projection.StudentCourseRowProjection;
import com.saadoun.e_learning.service.email.AbstractEmail;
import com.saadoun.e_learning.service.email.EmailFactory;
import com.saadoun.e_learning.service.email.EmailService;
import com.saadoun.e_learning.service.email.EmailType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saadoun.e_learning.dto.CourseDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {


      @Autowired
      StudentRepository studentRepository;

      @Autowired
      CourseRepository courseRepository;

      @Autowired
      EnrollmentRepository enrollmentRepository;

      @Autowired
      StudentMapper studentMapper;

      @Autowired
      EmailFactory emailFactory;

      @Autowired
      EmailService emailService;

      private final ApplicationEventPublisher eventPublisher;

      public StudentDto getByIdWithCourses(Long id){

          Student student =  studentRepository.findByIdWithCourses(id).orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
          return studentMapper.studentToStudentDto(student);
      }

    //      public StudentDto getByIdWithCourses(Long id){
    //        Student student =  studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
    //        return studentMapper.studentToStudentDto(student);
    //      }


    public List<StudentDto> getCourseStudent(String CourseName){
         return studentRepository.findAll(
                   StudentSpecification.hasCourse(CourseName)
           ).stream().map(studentMapper::studentToStudentDto).toList();
    }

    public List<StudentDto> getAllStudentsWithCourses() {
      List<StudentCourseRowProjection> rows = studentRepository.findAllWithCourses();
      LinkedHashMap<Long, StudentDto> studentsById = new LinkedHashMap<>();

      for (StudentCourseRowProjection row : rows) {
        StudentDto student = studentsById.computeIfAbsent(
            row.getStudentId(),
            id -> StudentDto.builder()
                .id(id)
                .name(row.getStudentName())
                .email(row.getStudentEmail())
                .courses(new ArrayList<>())
                .build()
        );

        if (row.getCourseId() != null) {
          student.getCourses().add(
              CourseDto.builder()
                  .id(row.getCourseId())
                  .title(row.getCourseName())
                  .build()
          );
        }
      }

      return new ArrayList<>(studentsById.values());
    }

    public Page<StudentDto> getAllStudents(Pageable pageable){
            Page<Student> page = studentRepository.findAllStudents(pageable);
            List<Long> ids = page.stream().map(Student::getId).toList();
            List<Student> students  = studentRepository.getStudentsWithCourses(ids);
            List<StudentDto> dtos =
                    students.stream()
                            .map(studentMapper::studentToStudentDto)
                            .toList();

            return new PageImpl<>(
                    dtos,
                    pageable,
                    page.getTotalElements()
            );
      }

    @Transactional
    public StudentDto createStudent(StudentReqDto reqDto){
          Student student = studentMapper.studentReqDtoToStudent(reqDto);
          Student savedStudent = studentRepository.save(student);
            return studentMapper.studentToStudentDto(savedStudent);
      }

      @Transactional
      public Enrollment enroll(EnrollReqDto dto){
          Course course   = courseRepository.findById(dto.getCourseId()).orElseThrow();
          Student student = studentRepository.findById(dto.getStudentId()).orElseThrow();
          Enrollment enrollment = Enrollment.builder()
                  .student(student)
                  .course(course)
                  .build();
          enrollmentRepository.save(enrollment);

          eventPublisher.publishEvent(new EnrollmentCreatedEvent(student,course));



          return enrollment;
      }


}
