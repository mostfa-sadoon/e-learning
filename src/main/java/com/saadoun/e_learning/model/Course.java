package com.saadoun.e_learning.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {


     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private String title;
     private String img;

     @OneToMany(mappedBy = "course")
     @JsonIgnore
     private List<Enrollment> enrollments = new ArrayList<>();

}
