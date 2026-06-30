package com.saadoun.e_learning.controller;

import com.saadoun.e_learning.dto.CourseDto;
import com.saadoun.e_learning.dto.StudentDto;
import com.saadoun.e_learning.dto.req.CourseReqDto;
import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.service.CourseService;
import com.saadoun.e_learning.service.StudentService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Slf4j
public class CourseController {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @GetMapping("course/student/{course_name}")
    public List<StudentDto> getCourseStudent(@PathVariable String course_name){
          log.info("get data of course here ");
          return studentService.getCourseStudent(course_name);
    }

    @PostMapping(value = "save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CourseDto> create(@ModelAttribute CourseReqDto dto)
    {
        CourseDto course = courseService.save(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(course);
    }


}
