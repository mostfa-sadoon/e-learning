package com.saadoun.e_learning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    public  Long id;
    public  String name;
    public  String email;
    List<CourseDto> courses = new ArrayList<>();
}
