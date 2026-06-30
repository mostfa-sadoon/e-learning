package com.saadoun.e_learning.mapper;

import com.saadoun.e_learning.dto.CourseDto;
import com.saadoun.e_learning.dto.StudentDto;
import com.saadoun.e_learning.dto.req.StudentReqDto;
import com.saadoun.e_learning.model.Address;
import com.saadoun.e_learning.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {

    public StudentDto studentToStudentDto(Student student){
        List<CourseDto> courses = student.getEnrollments()
                .stream()
                .map(e -> new CourseDto(
                        e.getCourse().getId(),
                        e.getCourse().getTitle(),
                        e.getCourse().getImg()))
                .toList();

        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                courses
        );
    }

    public Student studentReqDtoToStudent(StudentReqDto dto){
        Address address = Address.builder()
                .city(dto.getCity())
                .country(dto.getCountry())
                .street(dto.getStreet())
                .build();
         return Student.builder()
                 .name(dto.getName())
                 .email(dto.getEmail())
                 .address(address)
                 .build();
    }

}
