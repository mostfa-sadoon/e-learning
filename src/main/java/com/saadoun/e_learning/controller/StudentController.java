package com.saadoun.e_learning.controller;


import com.saadoun.e_learning.dto.StudentDto;
import com.saadoun.e_learning.dto.req.StudentReqDto;
import com.saadoun.e_learning.dto.req.EnrollReqDto;
import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    public StudentDto getByIdWithCourses(@PathVariable Long id){
        return studentService.getByIdWithCourses(id);
    }

    @GetMapping("/get-all")
    public List<StudentDto> getAllStudentsWithCourses(){
        return studentService.getAllStudentsWithCourses();
    }


    @GetMapping("/get-all-paginated")
    public Page<StudentDto> getAllStudents( @PageableDefault(page = 0, size = 10) Pageable pageable){
        return studentService.getAllStudents(pageable);
    }


    @PostMapping("store")
    public StudentDto createStudent(@RequestBody StudentReqDto dto){
        return   studentService.createStudent(dto);
    }

    @PostMapping("enroll")
    public ResponseEntity<?> enrollment(@RequestBody EnrollReqDto dto){
        Enrollment enrollment =  studentService.enroll(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }

}
