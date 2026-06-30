package com.saadoun.e_learning.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CourseDto {
    public  Long id;
    public  String title;
    public  String img;
}
